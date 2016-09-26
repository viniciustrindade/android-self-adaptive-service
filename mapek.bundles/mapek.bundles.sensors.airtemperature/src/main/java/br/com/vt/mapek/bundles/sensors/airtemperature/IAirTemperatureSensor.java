package br.com.vt.mapek.bundles.sensors.airtemperature;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IAirTemperatureSensor extends ISensor {

	public final IProperty property = Property.AMBIENTTEMPERATURE;

	public float getTemperatureLevel();
}
