package br.com.vt.mapek.services.domain;



public class Symptom {
	IProperty property;
	Condition condition;
	Threshold threshold;

	public Symptom(IProperty property, Condition condition, Threshold threshold) {
		this.property =property;
		this.condition = condition;
		this.threshold = threshold;
	}
	
	public Boolean check(ContextElement c){
		return (this.property == c.getProperty() && this.condition.check(c,this.threshold));
	}
	
	@Override
	public String toString() {
		
		return String.valueOf(property + " " + condition +" " +threshold );
	}
}
