package br.com.vt.mapek.services.domain;



public enum Condition {
	IGUAL("="),MENOR_IGUAL_QUE("<="),MENOR_QUE("<"),
	MAIOR_IGUAL_QUE(">="),MAIOR_QUE(">"),ENTRE("between");

	public String value;
	
	Condition(String value){
		this.value = value;
	}
	public Boolean check(ContextElement contextElement, Threshold threshold) {
		switch (this) {
		case IGUAL:
			return IGUAL(contextElement, threshold);
		case MENOR_IGUAL_QUE:
			return MENOR_IGUAL_QUE(contextElement, threshold);
		case MENOR_QUE:
			return MENOR_QUE(contextElement, threshold);
		case MAIOR_IGUAL_QUE:
			return MAIOR_IGUAL_QUE(contextElement, threshold);
		case MAIOR_QUE:
			return MAIOR_QUE(contextElement, threshold);
		case ENTRE:
			return ENTRE(contextElement, threshold);
		default:
			return null;
		}
	}

	public static Boolean IGUAL(ContextElement contextElement,
			Threshold threshold) {
		return (contextElement.getReading() == threshold.getLowerBound());
	}

	public static Boolean MENOR_IGUAL_QUE(ContextElement contextElement,
			Threshold threshold) {
		return (contextElement.getReading() <= threshold.getLowerBound());
	}

	public static Boolean MENOR_QUE(ContextElement contextElement,
			Threshold threshold) {
		return (contextElement.getReading() < threshold.getLowerBound());
	}

	public static Boolean MAIOR_IGUAL_QUE(ContextElement contextElement,
			Threshold threshold) {
		return (contextElement.getReading() >= threshold.getLowerBound());
	}

	public static Boolean MAIOR_QUE(ContextElement contextElement,
			Threshold threshold) {
		return (contextElement.getReading() < threshold.getLowerBound());
	}
	
	public static Boolean ENTRE(ContextElement contextElement,
			Threshold threshold) {
		return (contextElement.getReading() >= threshold.getLowerBound() && contextElement.getReading() <= threshold.getUpperBound());
	}

	
	public String toString() {
		return name();
	}
}
