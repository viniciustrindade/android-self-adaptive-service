package br.com.vt.mapek.services.domain;

public interface IBatterySensor extends ISensor {

	public final Property property = SystemProperty.BATERIA;

	public float getBatteryLevel();
}
