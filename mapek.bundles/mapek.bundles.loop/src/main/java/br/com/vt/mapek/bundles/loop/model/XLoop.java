package br.com.vt.mapek.bundles.loop.model;

import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Default(DefaultType.FIELD)
@Root(name = "loop")
public  class XLoop {

	public final static int DEFAULT_RATE = 10000;

	@Attribute(name = "rate", required = true)
	public int rate;

	@ElementList
	public List<XSensor> sensors;

	@ElementList
	public List<XPolicy> policys;

	@ElementList
	public List<XAction> actions;

	public XLoop() {
		this.rate = DEFAULT_RATE;
		this.sensors = new LinkedList<XSensor>();
		this.policys = new LinkedList<XPolicy>();
		this.actions = new LinkedList<XAction>();
	}

	public XLoop(int rate) {
		this.rate = rate;
		this.sensors = new LinkedList<XSensor>();
		this.policys = new LinkedList<XPolicy>();
		this.actions = new LinkedList<XAction>();
	}

	public XSensor instanceSensor(String className) {
		XSensor sensor = new XSensor(className);
		this.sensors.add(sensor);
		return sensor;
	}

	public XPolicy instancePolicy() {
		XPolicy policy = new XPolicy();
		this.policys.add(policy);
		return policy;
	}

	public XAction instanceAction(String id, String params) {
		XAction action = new XAction(id, params);
		this.actions.add(action);
		return action;
	}

}