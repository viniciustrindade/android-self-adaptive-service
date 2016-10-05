package br.com.vt.mapek.bundles.sensors.proximity;

import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.Property;


public interface IProximitySensor extends ISensor {

	public final IProperty property = Property.PROXIMITY;

	public float getProximityLevel();
}
