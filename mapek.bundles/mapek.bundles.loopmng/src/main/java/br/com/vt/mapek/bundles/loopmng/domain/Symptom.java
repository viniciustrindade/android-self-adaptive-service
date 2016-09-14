package br.com.vt.mapek.bundles.loopmng.domain;

import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.Property;
import br.com.vt.mapek.services.domain.Threshold;


public class Symptom {
	Property property;
	Condition condition;
	Threshold threshold;

	public Symptom(Property property, Condition condition, Threshold threshold) {
		this.property =property;
		this.condition = condition;
		this.threshold = threshold;
	}
	
	public Boolean check(ContextElement c){
		return (this.property == c.getProperty() && this.condition.check(c,this.threshold));
	}
}
