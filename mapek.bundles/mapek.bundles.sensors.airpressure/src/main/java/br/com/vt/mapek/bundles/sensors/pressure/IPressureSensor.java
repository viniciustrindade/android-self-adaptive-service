package br.com.vt.mapek.bundles.sensors.pressure;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IPressureSensor extends ISensor {

	public final IProperty property = Property.PRESSURE;

	public float getPressureLevel();
}
