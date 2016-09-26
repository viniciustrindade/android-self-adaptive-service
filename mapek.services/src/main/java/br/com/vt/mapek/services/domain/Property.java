package br.com.vt.mapek.services.domain;

public enum Property implements IProperty {
	BATTERY(0,100, "%"),
	AMBIENTTEMPERATURE(-273.1f, 100, "°C"), 
	PROXIMITY(0,10,"cm"),
	PRESSURE(330, 1100,"hPa"), 
	LIGHT(0, 40000, "lx"), 
	HUMIDITY(0, 100, "%");
	

	String unit;
	Threshold threshold;

	Property(float low, float up, String unit) {
		this.threshold = new Threshold(low, up);
		this.unit = unit;
	}

	public String getName() {
		return this.name();
	}

	public Threshold getThreshold() {
		// TODO Auto-generated method stub
		return this.threshold;
	}

	public String getUnit() {
		// TODO Auto-generated method stub
		return null;
	}

}
