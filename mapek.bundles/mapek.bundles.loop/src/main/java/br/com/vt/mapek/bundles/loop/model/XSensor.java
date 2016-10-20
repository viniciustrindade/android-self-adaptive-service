package br.com.vt.mapek.bundles.loop.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Root(name = "sensor")
@Default(DefaultType.FIELD)
public class XSensor {

	@Attribute(name = "className", required = true)
	public String className;

	public XSensor() {

	}

	public XSensor(String className) {
		this.className = className;
	}



}