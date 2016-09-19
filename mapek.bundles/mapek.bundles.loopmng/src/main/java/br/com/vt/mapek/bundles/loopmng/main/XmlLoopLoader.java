package br.com.vt.mapek.bundles.loopmng.main;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.bundles.loopmng.Constants;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XAction;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XPolicy;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XSensor;
import br.com.vt.mapek.bundles.loopmng.services.ISerializerService;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.Threshold;

@Component
@Instantiate
public class XmlLoopLoader implements Runnable {
	private volatile boolean end = false;

	private Bundle bundle;

	private BundleContext bundleContext;

	@Requires
	private ISerializerService serializer;

	@Requires
	private IFileService fileManager;

	@Requires
	private ILoggerService log;

	private List<Loop> loopers;

	private HashMap<String, ISensor> cache = new HashMap<String, ISensor>();

	public XmlLoopLoader() {
		this.loopers = new LinkedList<Loop>();
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.bundleContext = bundle.getBundleContext();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ISensor getSensorByClassName(String className) {
		ISensor isensor = null;

		// IF EXISTS IN CACHE
		if (cache.containsKey(className)) {
			return cache.get(className);
		}

		try {
			ServiceReference ref = bundleContext.getServiceReference(className);
			isensor = (ISensor) bundleContext.getService(ref);

			// CACHE SENSOR
			if (isensor != null) {
				cache.put(className, isensor);
			}

			return isensor;

		} catch (Exception e) {
			log.E("Couldnt load sensor class " + className + ", "
					+ e.getMessage());
		}
		return isensor;

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void registerLoopsFromLoopFile() throws RuntimeException {

		InputStream input = fileManager.getInputStream(Constants.LOOP_XML_FILE);
		XMLLoops xml;
		try {
			xml = serializer.unmarshal(input, XMLLoops.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		for (XLoop xmlloop : xml.loops) {
			Loop loop = new Loop(xmlloop.rate);

			for (XSensor sensor : xmlloop.sensors) {
				ISensor isensor = getSensorByClassName(sensor.className);
				if (isensor == null) {
					throw new RuntimeException("SENSOR NOT FOUND: "
							+ sensor.className);
				}
				loop.build(isensor);

			}

			for (XPolicy policy : xmlloop.policys) {
				loop.getSymptomRepository().addSymptom(policy.property,
						policy.condition, new Threshold(policy.setpoint));
			}

			for (XAction action : xmlloop.actions) {
				loop.getChangePlan().addAction(this.bundleContext,
						this.fileManager, action.id, action.params);
			}
			
			this.bundleContext.registerService(Loop.class, loop,
					new Hashtable());
		}
		
	

	}

	public void run() throws RuntimeException {
		log.I("Manager Iniciado!!");
		registerLoopsFromLoopFile();

		//INITIALIZE SENSORS
		for (String key:cache.keySet()){
			cache.get(key).register();
		}
		
		while (!end) {
			printBundles();
			for (Loop looper : loopers) {
				looper.run();
			}
		}
	}

	public void printBundles() {
		Bundle[] bundles = this.bundleContext.getBundles();
		String teststr = "Bundles Status: \n";
		log.D("=======================================");
		for (Bundle b : bundles) {

			String stateStr = "";
			int state = b.getState();
			switch (state) {

			case Bundle.ACTIVE:
				stateStr = "ACTIVE";
				break;
			case Bundle.INSTALLED:
				stateStr = "INSTALLED";
				break;
			case Bundle.RESOLVED:
				stateStr = "RESOLVED";
				break;
			case Bundle.STARTING:
				stateStr = "STARTING";
				break;
			case Bundle.STOPPING:
				stateStr = "STOPPING";
				break;
			case Bundle.UNINSTALLED:
				stateStr = "UNINSTALLED";
				break;

			}
			if (stateStr.length() == 0)
				stateStr = "UNKNOWN STATE";

			teststr = teststr + "\n\n [" + stateStr + "] "
					+ b.getSymbolicName() + " (" + b.getVersion() + ")";

		}
		log.D(teststr);
		log.D("=======================================");
	}

	@Bind(aggregate = true, specification = Loop.class, optional = false)
	public void bindLoops(Loop l) {
		log.I("BIND LOOPS");
		loopers.add(l);
	}

	@Validate
	public void starting() {
		log.I("Manager starting");
		Thread thread = new Thread(this);
		end = false;
		thread.start();
	}

	@Invalidate
	public void stopping() {
		log.I("Manager stopping");
		end = true;
	}

}
