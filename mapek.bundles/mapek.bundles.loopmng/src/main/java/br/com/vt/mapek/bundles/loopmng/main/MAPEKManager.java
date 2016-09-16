package br.com.vt.mapek.bundles.loopmng.main;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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
import br.com.vt.mapek.services.domain.ISensor;
import br.com.vt.mapek.services.domain.Threshold;

@Component
@Instantiate
public class MAPEKManager implements Runnable {
	private volatile boolean end = false;

	private Bundle bundle;

	private BundleContext bundleContext;

	@Requires
	private ILoggerService log;

	@Requires
	private IFileService fileManager;
	
	@Requires
	private ISerializerService serializer;
	
	private List<Loop> loopers;

	public MAPEKManager() {
		this.loopers = new LinkedList<Loop>();
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

	}

	public synchronized void run() {
		log.I("Manager Iniciado!!");
		try {
			configureFromXML(Constants.filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!end) {
			for (Loop looper : loopers) {
				looper.run();
				printBundles();
			}
		}
	}


	public synchronized void configureFromXML(String xmlFile) throws Exception {
		InputStream input = fileManager.getInputStream(Constants.filename);
		XMLLoops loops = serializer.unmarshal(input, XMLLoops.class);
/*
		XMLLoops loops = new XMLLoops();
		XLoop l1 = loops.instance();
		l1.instanceSensor(Util.getNewID(), IBatterySensor.class.getName());

		XPolicy policy1 = l1.instancePolicy();
		policy1.property = Constants.property;
		policy1.condition = Condition.MENOR_IGUAL_QUE;
		policy1.setpoint = 10;
		;
		for (String bundles : Constants.bundlesToStart) {
			l1.instanceAction(Commands.START_COMPONENT.name(), bundles);
		}

		XLoop l2 = loops.instance();
		l2.instanceSensor(Util.getNewID(), IBatterySensor.class.getName());

		XPolicy p2 = l2.instancePolicy();
		p2.property = Constants.property;
		p2.condition = Condition.MAIOR_IGUAL_QUE;
		p2.setpoint = 50;

		for (String bundles : Constants.bundlesToStop) {
			l2.instanceAction(Commands.STOP_COMPONENT.name(), bundles);
		}
*/
		int count = 0;
		for (XLoop xmlloop : loops.loops) {

			log.I("loop " + count);

			Loop loop = new Loop(xmlloop.rate);
			loopers.add(loop);

			for (XSensor sensor : xmlloop.sensors) {
				try {
					ServiceReference ref = bundleContext
							.getServiceReference(sensor.className);
					ISensor isensor = (ISensor) bundleContext.getService(ref);
					isensor.setSensorID(sensor.id);
					loop.build(isensor);
				} catch (Exception e) {
					log.E("Couldnt load sensor class " + sensor.className + ", " + 	e.getMessage());
				
				}
			}

			for (XPolicy policy : xmlloop.policys) {
				loop.getSymptomRepository().addSymptom(policy.property,
						policy.condition, new Threshold(policy.setpoint));
			}
			for (XAction action : xmlloop.actions) {
				loop.getChangePlan().addAction(this.bundleContext,
						this.fileManager, action.id, action.params);
			}

		}
	}

	public synchronized void printBundles(){
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

	@Validate
	public synchronized void starting() {
		log.I("Manager starting");
		Thread thread = new Thread(this);
		end = false;
		thread.start();
	}

	@Invalidate
	public synchronized void stopping() {
		log.I("Manager stopping");
		end = true;
	}

}
