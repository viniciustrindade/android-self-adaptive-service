/*package br.com.vt.mapek.bundles.loopmng.services.context;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.PostRegistration;
import org.apache.felix.ipojo.annotations.PostUnregistration;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.ServiceReference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.context.IBatteryContextService;
import br.com.vt.mapek.services.domain.ContextElement;

@Component
@Provides
@Instantiate
public class BatteryContextService implements IBatteryContextService {
	
	//@Requires(optional=true,nullable=true)
	@Requires
	private Context androidContext;

	@Requires
	private ILoggerService log;

	public ContextElement getCurrentContext() {
		int reading = (int) getBatteryLevel();
		Date collectionTime = new Date();
		ContextElement context = new ContextElement(property);
		context.setCollectionTime(collectionTime);
		context.setReading(reading);
		
	//	log.I(Util.dtFormat.format(collectionTime) + " | " + reading + "\n");
		return context;
	}

	public float getBatteryLevel() {
		if (androidContext == null){
			return 0f;
		}
		
		Intent batteryIntent = androidContext.registerReceiver(null,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		// Error checking that probably isn't needed but I added just in case.
		if (level == -1 || scale == -1) {
			return 50.0f;
		}

		return ((float) level / (float) scale) * 100.0f;
	}
	
	@Validate
	public void start() {
		log.D("[START] Iniciado Battery Context Service");
		
	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado  Battery Context Service");

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
*/