package br.com.vt.mapek.loopmng;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.vt.mapek.bundles.loopmng.Constants;
import br.com.vt.mapek.bundles.loopmng.domain.Commands;
import br.com.vt.mapek.bundles.loopmng.domain.Condition;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XAction;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XPolicy;
import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops.XLoop.XSensor;
import br.com.vt.mapek.bundles.loopmng.monitor.sensors.BatterySensor;
import br.com.vt.mapek.bundles.loopmng.services.FileService;
import br.com.vt.mapek.bundles.loopmng.services.SerializerService;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ISerializerService;
import br.com.vt.mapek.services.common.Util;

public class SerializerManagerTest {
	public static ISerializerService<XMLLoops> serializerManager = new SerializerService();
	XMLLoops loops;
	private InputStream input;
	private OutputStream output;
	private IFileService fileManager;
	
	public SerializerManagerTest() throws  IOException {

	}
	@Before
	public void init()throws  Exception,IOException {
	
		loops = new XMLLoops();
		this.fileManager = new FileService();
		marshalLoop1();
		marshalLoop2();
		this.input = fileManager.getInputStream(Constants.filename);
		this.output = fileManager.getOutputStream(Constants.filename);
		serializerManager.marshal(output, loops);
	}

	public void marshalLoop1() throws  IOException {
		XLoop l1 = loops.instance();
		l1.instanceSensor(Util.getNewID(),BatterySensor.class.getName());

		XPolicy policy1 = l1.instancePolicy();
		policy1.property = Constants.property;
		policy1.condition = Condition.MENOR_IGUAL_QUE;
		policy1.setpoint = 10;
		;
		for (String bundles : Constants.bundlesToStart) {
			l1.instanceAction(Commands.START_COMPONENT.name(), bundles);
		}

	
	}

	public void marshalLoop2() throws  IOException{

		XLoop l2 = loops.instance();
		l2.instanceSensor(Util.getNewID(),BatterySensor.class.getName());

		XPolicy p2 = l2.instancePolicy();
		p2.property = Constants.property;
		p2.condition = Condition.MAIOR_IGUAL_QUE;
		p2.setpoint = 50;

		for (String bundles : Constants.bundlesToStop) {
			l2.instanceAction(Commands.STOP_COMPONENT.name(), bundles);
		}


	}
	@Test
	public void umarshalLoop1() throws Exception {
		XMLLoops loops = serializerManager.unmarshal(
				input, XMLLoops.class);
		XLoop l1 = loops.loops.get(0);

		XSensor s1 = l1.sensors.get(0);
		Assert.assertEquals(s1.className, Constants.property);

		XPolicy p1 = l1.policys.get(0);
		Assert.assertEquals(p1.property, Constants.property);
		Assert.assertEquals(p1.condition, Condition.MENOR_IGUAL_QUE);

		Assert.assertEquals(p1.setpoint, 10);
		int count = 0;
		for (String bundle : Constants.bundlesToStart) {
			XAction action = l1.actions.get(count);
			Assert.assertEquals(action.id, Commands.START_COMPONENT.name());
			Assert.assertEquals(action.params, bundle);
			count++;
		}

	}

	@Test
	public void umarshalLoop2() throws  Exception {

		XMLLoops loops = serializerManager.unmarshal(
				input, XMLLoops.class);
		// / LOOOP 2
		XLoop l2 = loops.loops.get(1);

		XSensor s2 = l2.sensors.get(0);
		Assert.assertEquals(s2.className, Constants.property);

		XPolicy p2 = l2.policys.get(0);
		Assert.assertEquals(p2.property, Constants.property);
		Assert.assertEquals(p2.condition, Condition.MAIOR_IGUAL_QUE);

		Assert.assertEquals(p2.setpoint, 50);
		int count = 0;
		for (String bundle : Constants.bundlesToStop) {
			XAction action = l2.actions.get(count);
			Assert.assertEquals(action.id, Commands.STOP_COMPONENT.name());
			Assert.assertEquals(action.params, bundle);
			count++;
		}
	}

}
