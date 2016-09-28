package br.com.vt.mapek.android.felix;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import android.content.Context;
import android.content.res.Resources;

public class Installer implements BundleActivator {
	private Context androidContext;
	private Resources res;
	private BundleContext m_context = null;
	private final String[] files = {
			"ipojo",
			"ipojoapi",
			"ipojocomposite",
			"mb_logger",
			"mb_sensor_battery",
			"mb_sensor_pressure",
			"mb_sensor_airtemperature",
			"mb_sensor_humidity",
			"mb_sensor_illuminance",
			"mb_sensor_proximity",
			"mb_resources",
			"mb_symptomrepository",
			"mb_contextlog",
			"mb_monitor",
			"mb_analyzer",
			"mb_planner",
			"mb_execute",
			"mb_loop",
			"mb_validator"
			};


	public Installer(Context androidContext) {
		this.androidContext = androidContext;
		this.res = androidContext.getResources();
	}

	public Bundle installBundle(BundleContext context, String filename)
			throws Exception {
		int idRes = res.getIdentifier(filename, "raw",
				androidContext.getPackageName());
		return context.installBundle("file:///" + filename + "jar",
				res.openRawResource(idRes));
	}

	@SuppressWarnings("rawtypes")
	public void start(BundleContext context) throws Exception {
		m_context = context;
		for (String file : files) {
			Bundle bundle = this.installBundle(context, file);
			bundle.start();
		}

	}

	public void stop(BundleContext arg0) throws Exception {

	}

	public BundleContext getContext() {
		return m_context;
	}

	public Bundle[] getBundles() {
		if (m_context != null) {
			return m_context.getBundles();
		}
		return null;
	}

	public <S> ServiceRegistration<S> registerBundleService(
			BundleContext context, Class<S> clazz, S service,
			Dictionary<String, ?> properties) {
		return context.registerService(clazz, service, properties);
	}

}
