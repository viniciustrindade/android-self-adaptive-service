package br.com.vt.mapek.bundles.loopmng.monitor.sensors;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.PostRegistration;
import org.apache.felix.ipojo.annotations.PostUnregistration;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.bundles.loopmng.services.context.BatteryContextService;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.context.IBatteryContextService;
import br.com.vt.mapek.services.domain.IBatterySensor;

@Component
@Provides
@Instantiate
public class BatterySensor extends AbstractSensor implements IBatterySensor {

	@Requires
	private ILoggerService log;

	@Requires
	private IBatteryContextService batteryContext;

	public BatterySensor() {
	}


	public void readAndNotify() {
		log.D("[Medição]");
		this.notifyObservers(batteryContext.getCurrentContext());
	}

	@Validate
	public void start() {
		log.D("[START] Iniciado Battery Sensor");

	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado  Battery Sensor");

	}

	@PostRegistration
	public void registered(ServiceReference ref) {
		log.D("Registered id: " + ref.getProperty("service.id"));

	}

	@PostUnregistration
	public void unregistered(ServiceReference ref) {
		log.D("Unregistered by " + ref.getBundle().getSymbolicName());
	}
}
