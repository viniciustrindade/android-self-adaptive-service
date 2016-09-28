package br.com.vt.mapek.bundles.loop.main;

import java.util.Dictionary;
import java.util.List;

import model.LoopXml.XLoop.XAction;
import model.LoopXml.XLoop.XPolicy;
import model.LoopXml.XLoop.XSensor;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.extender.DeclarationBuilderService;
import org.apache.felix.ipojo.util.Log;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.bundles.loop.Action;
import br.com.vt.mapek.services.IAnalyzer;
import br.com.vt.mapek.services.IChangePlan;
import br.com.vt.mapek.services.IExecutor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ILoop;
import br.com.vt.mapek.services.IMonitor;
import br.com.vt.mapek.services.IPlanner;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.ISymptomRepository;
import br.com.vt.mapek.services.domain.Commands;
import br.com.vt.mapek.services.domain.ISystemContextLog;
import br.com.vt.mapek.services.domain.Symptom;
import br.com.vt.mapek.services.domain.Threshold;

@Component(name = "loop-factory")
@Provides(strategy = "instance")
public class Loop implements ILoop {

	@Property(mandatory = true)
	private int id;

	@Property(mandatory = true)
	private int rate;

	@Property(mandatory = true)
	private List<XSensor> sensors;

	@Property(mandatory = true)
	private List<XAction> actions;

	@Property(mandatory = true)
	private List<XPolicy> policys;

	@Requires
	private ISymptomRepository symptomRepository;

	@Requires
	private ISystemContextLog systemContextLog;

	@Requires
	private IChangePlan changePlan;

	@Requires
	private IMonitor monitor;

	@Requires
	private IAnalyzer analyzer;

	@Requires
	private IPlanner planner;

	@Requires
	private IExecutor executor;

	@Requires
	private ILoggerService log;

	@Requires
	private IResource resource;

	@Requires
	private DeclarationBuilderService service;

	private Bundle bundle;
	private BundleContext context;

	public Loop() {
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.context = bundle.getBundleContext();

	}

	@Validate
	public synchronized void run() {
		log.I("[" + this.id + "] RUN");

		monitor.setLoop(this);
		log.I("[" + this.id + "] MONITOR: " + monitor);
		analyzer.setLoop(this);
		log.I("[" + this.id + "] MONITOR: " + analyzer);
		planner.setLoop(this);
		executor.setLoop(this);
		monitor.register(analyzer);
		analyzer.register(planner);
		planner.register(executor);

		/********* ACTION **********/
		for (XAction action : actions) {
			this.changePlan.addAction(new Action(context, resource, log,
					Commands.valueOf(action.id), action.params));
		}
		/********* POLICY **********/
		for (XPolicy policy : policys) {
			this.symptomRepository.addSymptom(new Symptom(policy.property,
					policy.condition, new Threshold(policy.bound)));
		}
		/********* SENSOR **********/
		for (XSensor sensor : sensors) {
			ISensor isensor = getSensorByClassName(sensor.className);

			if (isensor == null) {
				throw new RuntimeException("SENSOR NOT FOUND: "
						+ sensor.className);
			} else {
				log.D("SENSOR FOUNDED: " + isensor + "\n");
			}
			isensor.register(monitor);

			// Initialize
			isensor.register();

		}

		/*
		 * try { Thread.sleep(rate); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public ISymptomRepository getSymptomRepository() {
		return symptomRepository;
	}

	public void setSymptomRepository(ISymptomRepository symptomRepository) {
		this.symptomRepository = symptomRepository;
	}

	public IChangePlan getChangePlan() {
		return changePlan;
	}

	public void setChangePlan(IChangePlan changePlan) {
		this.changePlan = changePlan;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ISensor getSensorByClassName(String className) {
		ISensor isensor = null;
		try {
			ServiceReference ref = context.getServiceReference(className);
			isensor = (ISensor) context.getService(ref);
			return isensor;

		} catch (Exception e) {
			log.E("Couldnt load sensor class " + className + ", "
					+ e.getMessage() + "\n");
		}
		return isensor;

	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		String retorno = "\nLOOP[" + id + "][" + this.hashCode() + "] ";
		return retorno;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<XSensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<XSensor> sensors) {
		this.sensors = sensors;
	}

	public List<XAction> getActions() {
		return actions;
	}

	public void setActions(List<XAction> actions) {
		this.actions = actions;
	}

	public List<XPolicy> getPolicys() {
		return policys;
	}

	public void setPolicys(List<XPolicy> policys) {
		this.policys = policys;
	}

	public ISystemContextLog getSystemContextLog() {
		return systemContextLog;
	}

	public void setSystemContextLog(ISystemContextLog systemContextLog) {
		this.systemContextLog = systemContextLog;
	}

	public IMonitor getMonitor() {
		return monitor;
	}

	public void setMonitor(IMonitor monitor) {
		this.monitor = monitor;
	}

	public ILoggerService getLog() {
		return log;
	}

	public void setLog(ILoggerService log) {
		this.log = log;
	}

	public <S> S registerService(Dictionary<String, String> properties,
			Class<S> clazz, S service) {
		context.registerService(clazz, service, properties);
		return service;
	}
}
