package br.com.vt.mapek.bundles.sensors.battery;

import java.util.Date;
import java.util.Random;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import br.com.vt.mapek.services.ABSensor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.domain.ContextElement;

@Component
@Provides(specifications = IBatterySensor.class)
@Instantiate
public class BatterySensor extends ABSensor implements IBatterySensor {

	@Requires
	private ILoggerService log;
	private Thread registry;

	public BatterySensor() {
	}

	public void readAndNotify() {
		log.I("[Medição] " + this.getClass().getName() + "\n");
		this.notifyObservers(getCurrentContext());
	}

	public ContextElement getCurrentContext() {
		int reading = (int) getBatteryLevel();
		Date collectionTime = new Date();
		ContextElement context = new ContextElement(property);
		context.setCollectionTime(collectionTime);
		context.setReading(reading);

		// log.I(Util.dtFormat.format(collectionTime) + " | " + reading + "\n");
		return context;
	}

	public float getBatteryLevel() {
		Random r = new Random();

		return r.nextFloat() % 100;
	}

	public void register() {
		log.D("[REGISTER] Battery Sensor");
		registry = new Thread() {
			@Override
			public void run() {
				super.run();
				while(true){
					readAndNotify();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}

	public void unregister() {
		registry.interrupt();
	}

	@Validate
	public void start() {
		log.D("[START] Iniciado Battery Sensor");

	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado  Battery Sensor");

	}

}
