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
		Dictionary<String, String> props;
		m_context = context;

		Bundle org_osgi_compendium = this.installBundle(context,
				"org_osgi_compendium");
		Bundle ipojo = this.installBundle(context, "ipojo");
		Bundle mb_sensorbattery = this.installBundle(context,
				"mb_sensorbattery");
		Bundle mb_loopmng = this.installBundle(context, "mb_loopmng");
		Bundle mb_validator = this.installBundle(context, "mb_validator");

		org_osgi_compendium.start();
		ipojo.start();
		
		mb_sensorbattery.start();
		
		mb_loopmng.start();
		mb_validator.start();

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
