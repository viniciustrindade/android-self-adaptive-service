package br.com.vt.mapek.loopmng;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import br.com.vt.mapek.bundles.loopmng.Constants;
import br.com.vt.mapek.bundles.loopmng.analyze.Analyzer;
import br.com.vt.mapek.bundles.loopmng.analyze.SymptomRepository;
import br.com.vt.mapek.bundles.loopmng.domain.Action;
import br.com.vt.mapek.bundles.loopmng.domain.Commands;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XAction;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XPolicy;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XSensor;
import br.com.vt.mapek.bundles.loopmng.executor.Executor;
import br.com.vt.mapek.bundles.loopmng.main.Loop;
import br.com.vt.mapek.bundles.loopmng.monitor.Monitor;
import br.com.vt.mapek.bundles.loopmng.monitor.sensors.AbstractSensor;
import br.com.vt.mapek.bundles.loopmng.monitor.sensors.BatterySensor;
import br.com.vt.mapek.bundles.loopmng.plan.ChangePlan;
import br.com.vt.mapek.bundles.loopmng.plan.Planner;
import br.com.vt.mapek.bundles.loopmng.services.FileService;
import br.com.vt.mapek.bundles.loopmng.services.ISerializerService;
import br.com.vt.mapek.bundles.loopmng.services.SerializerService;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.domain.Threshold;

class MapekManagerTest {
	private ISerializerService serializer;
	private InputStream input;
	private XMLLoops loops;
	private List<Loop> loopers;
	private IFileService fileManager;

	@Before
	public void init() throws Exception {
		this.serializer = new SerializerService();
		this.fileManager = new FileService();
		this.input= fileManager.getInputStream(Constants.filename);
		this.loopers = new LinkedList<Loop>();
		loops = serializer.unmarshal(this.input, XMLLoops.class);

	}

	@Test
	public void testCicle() {
		XLoop xmlloop = loops.loops.get(1);
		Loop loop = new Loop(xmlloop.rate);
		loopers.add(loop);

		XSensor xmlsensor = xmlloop.sensors.get(0);
		AbstractSensor sensor = new BatterySensor();
		loop.addSensor(sensor);

		// MONITOR
		Monitor monitor = new Monitor();
		sensor.register(monitor);
		loop.run();
		Assert.assertTrue(!monitor.getSysStates().getSystemStates().isEmpty());

		// ANALYZE
		Analyzer analyzer = new Analyzer(loop);
		monitor.register(analyzer);
		// PLANNER
		Planner plan = new Planner(loop);
		analyzer.register(plan);

		// EXECUTOR
		Executor executor = new Executor();
		plan.register(executor);

		XPolicy xpolicy = xmlloop.policys.get(0);
		SymptomRepository symptomRepository = loop.getSymptomRepository();
		symptomRepository.addSymptom(xpolicy.property, xpolicy.condition,
				new Threshold(xpolicy.setpoint));

		for (XAction xaction : xmlloop.actions) {
			ChangePlan changePlan = loop.getChangePlan();
			Action action = new Action(Commands.valueOf(xaction.id), xaction.params);
			action.setFileManager(new FileService());
			action.setBundleContext(new BundleContext() {
				
				public boolean ungetService(ServiceReference<?> reference) {
					// TODO Auto-generated method stub
					return false;
				}
				
				public void removeServiceListener(ServiceListener listener) {
					// TODO Auto-generated method stub
					
				}
				
				public void removeFrameworkListener(FrameworkListener listener) {
					// TODO Auto-generated method stub
					
				}
				
				public void removeBundleListener(BundleListener listener) {
					// TODO Auto-generated method stub
					
				}
				
				public <S> ServiceRegistration<S> registerService(Class<S> clazz,
						ServiceFactory<S> factory, Dictionary<String, ?> properties) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public <S> ServiceRegistration<S> registerService(Class<S> clazz,
						S service, Dictionary<String, ?> properties) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public ServiceRegistration<?> registerService(String clazz, Object service,
						Dictionary<String, ?> properties) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public ServiceRegistration<?> registerService(String[] clazzes,
						Object service, Dictionary<String, ?> properties) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Bundle installBundle(String location, InputStream input)
						throws BundleException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Bundle installBundle(String location) throws BundleException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public <S> Collection<ServiceReference<S>> getServiceReferences(
						Class<S> clazz, String filter) throws InvalidSyntaxException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public ServiceReference<?>[] getServiceReferences(String clazz,
						String filter) throws InvalidSyntaxException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public <S> ServiceReference<S> getServiceReference(Class<S> clazz) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public ServiceReference<?> getServiceReference(String clazz) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public <S> ServiceObjects<S> getServiceObjects(ServiceReference<S> reference) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public <S> S getService(ServiceReference<S> reference) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getProperty(String key) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public File getDataFile(String filename) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Bundle[] getBundles() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Bundle getBundle(String location) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Bundle getBundle(long id) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Bundle getBundle() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public ServiceReference<?>[] getAllServiceReferences(String clazz,
						String filter) throws InvalidSyntaxException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Filter createFilter(String filter) throws InvalidSyntaxException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public void addServiceListener(ServiceListener listener, String filter)
						throws InvalidSyntaxException {
					// TODO Auto-generated method stub
					
				}
				
				public void addServiceListener(ServiceListener listener) {
					// TODO Auto-generated method stub
					
				}
				
				public void addFrameworkListener(FrameworkListener listener) {
					// TODO Auto-generated method stub
					
				}
				
				public void addBundleListener(BundleListener listener) {
					// TODO Auto-generated method stub
					
				}
			});
			changePlan.getCorrectiveActions().add(action);
		}

		ChangePlan changePlan = loop.getChangePlan();

		for (Action action : changePlan.getCorrectiveActions()) {
			System.out.println(action.getParams());
		}
		Assert.assertTrue(changePlan.getCorrectiveActions().get(0).getParams()
				.equals(Constants.bundlesToStart[0]));

		try {
			loop.run();
		} catch (Exception e) {

		}
	}

}
