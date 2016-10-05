package br.com.vt.mapek.bundles.sensors.battery;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IBatterySensor extends ISensor {

	public final IProperty property = Property.BATTERY;

	public float getBatteryLevel();
}
