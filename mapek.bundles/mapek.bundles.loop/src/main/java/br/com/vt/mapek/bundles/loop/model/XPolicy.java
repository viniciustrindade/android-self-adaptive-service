package br.com.vt.mapek.bundles.loop.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

import br.com.vt.mapek.services.domain.Condition;
import br.com.vt.mapek.services.domain.Property;

@Root(name = "policy")
@Default(DefaultType.FIELD)
public class XPolicy {
	@Attribute(name = "property", required = true)
	public Property property;

	@Attribute(name = "condition", required = true)
	public Condition condition;

	@Attribute(name = "bound", required = true)
	public int bound;
	
	@Attribute(name = "upperbound", required = false)
	public int upperbound;
	
	@Attribute(name = "lowerbound", required = false)
	public int lowerbound;

}
