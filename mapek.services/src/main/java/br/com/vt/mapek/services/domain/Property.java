package br.com.vt.mapek.services.domain;

public enum Property implements IProperty {
	BATTERY(0,100, "%"),
	AMBIENTTEMPERATURE(-273.1f, 100, "°C"), 
	PROXIMITY(0,10,"cm"),
	PRESSURE(330, 1100,"hPa"), 
	LIGHT(0, 40000, "lx"), 
	HUMIDITY(0, 100, "%");
	

	String unit;
	Threshold defaultThreshold;

	Property(float low, float up, String unit) {
		this.defaultThreshold = new Threshold(low, up);
		this.unit = unit;
	}

	public String getName() {
		return this.name();
	}

	public Threshold getDefaultThreshold() {
		return this.defaultThreshold;
	}

	public String getUnit() {
		return unit;
	}
	
	@Override
	public String toString() {
		
		return name() + "(" + unit+ ")";
	}

}
