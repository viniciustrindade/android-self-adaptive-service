package br.com.vt.mapek.bundles.sensors.illuminance;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import br.com.vt.mapek.services.ABSensor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.domain.ContextElement;

@Component
@Provides(specifications = IIlluminanceSensor.class)
@Instantiate
public class IlluminanceSensor extends ABSensor implements
		IIlluminanceSensor, SensorEventListener {

	@Requires
	private ILoggerService log;

	@Requires
	private Context context;

	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private float lastMeasure = 0f;

	public void onSensorChanged(SensorEvent event) {
		lastMeasure = event.values[0];
		log.I("\n [Medição] " + this.getClass().getName() + "\n");
		this.notifyObservers(getCurrentContext());
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public ContextElement getCurrentContext() {
		Date collectionTime = new Date();
		ContextElement context = new ContextElement(property);
		context.setCollectionTime(collectionTime);
		context.setValue(lastMeasure);
		// log.I(Util.dtFormat.format(collectionTime) + " | " + reading + "\n");
		return context;
	}

	public float getIlluminanceLevel(){
		return lastMeasure;

	}

	public void register() {
		log.D("[REGISTER] [" + property.getName() + "] Sensor");
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	public void unregister() {
		if (mSensorManager != null)
			mSensorManager.unregisterListener(this);
	}

	@Validate
	public void start() {
		log.D("[START] Iniciado [" + property.getName() + "] Sensor");
		mSensorManager =  (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado  [" + property.getName() + "] Sensor");
		unregister();

	}


}
