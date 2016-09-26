package model;

import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import br.com.vt.mapek.services.domain.Condition;
import br.com.vt.mapek.services.domain.Property;

@Root(name = "loops")
@Default(DefaultType.FIELD)
public class LoopXml  {
	@ElementList
	public List<XLoop> loops;

	public LoopXml() {
		this.loops = new LinkedList<XLoop>();
	}

	public XLoop instance() {
		XLoop lop = new XLoop(XLoop.DEFAULT_RATE);
		this.loops.add(lop);

		return lop;
	}

	public List<XLoop> getLoops() {
		return loops;
	}

	@Default(DefaultType.FIELD)
	@Root(name = "loop")
	public static class XLoop {

		private final static int DEFAULT_RATE = 10000;

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


		@Root(name = "sensor")
		@Default(DefaultType.FIELD)
		public static class XSensor {

			@Attribute(name = "className", required = true)
			public String className;

			public XSensor() {

			}

			public XSensor(String className) {
				this.className = className;
			}

	

		}

		@Root(name = "policy")
		@Default(DefaultType.FIELD)
		public static class XPolicy {
			@Attribute(name = "property", required = true)
			public Property property;

			@Attribute(name = "condition", required = true)
			public Condition condition;

			@Attribute(name = "bound", required = true)
			public int bound;

		}

		@Root(name = "action")
		@Default(DefaultType.FIELD)
		public static class XAction {
			@Attribute(name = "id", required = true)
			public String id;
			@Attribute(name = "params", required = true)
			public String params;

			public XAction() {

			}

			public XAction(String id, String params) {
				super();
				this.id = id;
				this.params = params;
			}

		}

	}

}
