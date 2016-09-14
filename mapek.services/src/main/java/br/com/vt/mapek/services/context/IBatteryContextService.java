package br.com.vt.mapek.services.context;

import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.Property;
import br.com.vt.mapek.services.domain.SystemProperty;

public interface IBatteryContextService {
	public final Property property = SystemProperty.BATERIA;

	public ContextElement getCurrentContext();

	public float getBatteryLevel();

}
