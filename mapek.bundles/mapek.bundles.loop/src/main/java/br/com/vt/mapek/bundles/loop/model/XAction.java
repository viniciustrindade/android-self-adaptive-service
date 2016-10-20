package br.com.vt.mapek.bundles.loop.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

@Root(name = "action")
@Default(DefaultType.FIELD)
public class XAction {
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
