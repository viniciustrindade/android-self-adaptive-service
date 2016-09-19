package br.com.vt.mapek.bundles.sensors.battery;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.Property;
import br.com.vt.mapek.services.domain.SystemProperty;


public interface IBatterySensor extends ISensor {

	public final Property property = SystemProperty.BATERIA;

	public float getBatteryLevel();
}
