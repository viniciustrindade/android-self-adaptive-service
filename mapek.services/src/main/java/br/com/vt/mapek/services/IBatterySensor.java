package br.com.vt.mapek.services;

import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IBatterySensor extends ISensor {

	public final IProperty property = Property.BATTERY;

	public float getBatteryLevel();
}
