package br.com.vt.mapek.bundles.loopmng.monitor.sensors;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.IBatterySensor;

@Component
@Provides
@Instantiate
public class BatterySensor extends AbstractSensor implements IBatterySensor {

	@Requires
	private ILoggerService log;

	@Requires
	private Context context;

	public BatterySensor() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		readAndNotify();
	}

	public void readAndNotify() {
		log.D("[Medição]");
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
		if (context == null) {
			return 0f;
		}
		Intent batteryIntent = context.registerReceiver(null, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		// Error checking that probably isn't needed but I added just in case.
		if (level == -1 || scale == -1) {
			return 50.0f;
		}

		return ((float) level / (float) scale) * 100.0f;
	}

	public void register() {
		log.D("[REGISTER] Battery Sensor");
		context.registerReceiver(this, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

	}
	public void unregister(){
		context.unregisterReceiver(this);
	}

	@Validate
	public void start() {
		log.D("[START] Iniciado Battery Sensor");
		register();
	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado  Battery Sensor");
		unregister();
	}


}
