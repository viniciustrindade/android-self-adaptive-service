package br.com.vt.mapek.desktop.main;

import java.io.InputStream;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Installer implements BundleActivator {

	private BundleContext m_context = null;

	public Installer() {
	}

	public Bundle installBundle(BundleContext context, String filename)
			throws Exception {
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream(filename);
		return context.installBundle("file:///" + filename + "jar", input);
	}

	@SuppressWarnings("rawtypes")
	public void start(BundleContext context) throws Exception {
		Dictionary<String, String> props;
		m_context = context;

		Bundle ipojo = this.installBundle(context, "ipojo.bundle");
		Bundle mb_loopmng = this.installBundle(context, "mb_loopmng.bundle");
		ipojo.start();
		mb_loopmng.start();
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
