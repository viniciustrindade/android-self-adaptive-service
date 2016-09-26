package br.com.vt.mapek.bundles.sensors.illuminance;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IIlluminanceSensor extends ISensor {

	public final IProperty property = Property.LIGHT;

	public float getIlluminanceLevel();
}
