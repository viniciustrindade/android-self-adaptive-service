package br.com.vt.mapek.bundles.sensors.humidity;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IHumiditySensor extends ISensor {

	public final IProperty property = Property.HUMIDITY;

	public float getHumidityLevel();
}
