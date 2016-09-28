package br.com.vt.mapek.bundles.loop;
/*package br.com.vt.mapek.bundles.loopmng.main;

import java.io.InputStream;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.extender.DeclarationHandle;
import org.apache.felix.ipojo.extender.InstanceBuilder;
import org.apache.felix.ipojo.extender.internal.declaration.service.DefaultDeclarationBuilderService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.bundles.loopmng.Constants;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop;
import br.com.vt.mapek.bundles.loopmng.services.ISerializerService;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.common.Util;

@Component
@Instantiate
public class XmlLoopLoader implements Runnable {
	private volatile boolean end = false;

	private Bundle bundle;

	private BundleContext context;

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
		this.context = bundle.getBundleContext();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ISensor getSensorByClassName(String className) {
		ISensor isensor = null;

		// IF EXISTS IN CACHE
		if (cache.containsKey(className)) {
			return cache.get(className);
		}

		try {
			ServiceReference ref = context.getServiceReference(className);
			isensor = (ISensor) context.getService(ref);

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


	private DefaultDeclarationBuilderService service;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void registerLoopsFromLoopFile() throws RuntimeException {

		// READ FILE
		InputStream input = fileManager.getInputStream(Constants.LOOP_XML_FILE);
		XMLLoops xml;
		try {
			xml = serializer.unmarshal(input, XMLLoops.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		// CREATE LOOPS
		for (XLoop xmlloop : xml.loops) {

			Dictionary<String, Object> configuration = new Hashtable<String, Object>();
			configuration.put("id", Util.getNewID());
			configuration.put("rate", xmlloop.rate);

			InstanceBuilder builder = service.newInstance("loop-factory");
			
			DeclarationHandle handle = builder.name("loop"+Util.getNewID())
					.configure()
					.property("id", Util.getNewID())
					.property("rate", xmlloop.rate)
					.build();
			
			handle.publish();

			
			 * Monitor mon = new Monitor(); Analyzer ana = new Analyzer(loop);
			 * Planner plan = new Planner(loop); Executor exec = new Executor();
			 * mon.register(ana); ana.register(plan); plan.register(exec);
			 * 
			 * for (XSensor sensor : xmlloop.sensors) { ISensor isensor =
			 * getSensorByClassName(sensor.className); if (isensor == null) {
			 * throw new RuntimeException("SENSOR NOT FOUND: " +
			 * sensor.className); } isensor.register(mon); }
			 * 
			 * registerService(SymptomRepository.class, new
			 * SymptomRepository());
			 * 
			 * for (XPolicy policy : xmlloop.policys) {
			 * registerService(Symptom.class, new Symptom(policy.property,
			 * policy.condition, new Threshold(policy.setpoint))); }
			 * 
			 * registerService(ChangePlan.class, new ChangePlan());
			 * 
			 * for (XAction action : xmlloop.actions) {
			 * registerService(Action.class, new
			 * Action(Commands.valueOf(action.id), action.params)); }
			 

		}

	}

	public <S> S registerService(Dictionary<String, String> properties,
			Class<S> clazz, S service) {
		context.registerService(clazz, service, properties);
		return service;
	}

	public synchronized void run() throws RuntimeException {
		log.I("Manager Iniciado!!");
		registerLoopsFromLoopFile();

		// INITIALIZE SENSORS
		for (String key : cache.keySet()) {
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
		Bundle[] bundles = this.context.getBundles();
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
*/