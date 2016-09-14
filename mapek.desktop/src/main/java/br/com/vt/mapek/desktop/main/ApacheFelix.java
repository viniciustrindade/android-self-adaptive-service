package br.com.vt.mapek.desktop.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleException;

import android.content.Context;

public class ApacheFelix {
	FelixConfig felixConfig;
	Felix felix;
	Installer installer;

	public ApacheFelix() {
		felixConfig = new FelixConfig();
		felixConfig.configureBundlesDir();
		felixConfig.configureCache();
		delete(felixConfig.getCacheDir());
		try {
			List<BundleActivator> activators = new ArrayList<BundleActivator>();
			installer = new Installer();
			activators.add(installer);
			// activators.add(new FileInstall());
			felixConfig.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
					activators);
			felix = new Felix(felixConfig);
			felix.start();

			// Android Context
			/*installer.registerBundleService(felix.getBundleContext(),
					Context.class,null,
					new Hashtable<String, String>());*/

			/*
			 * 
			 * ServiceRegistration<DeclarationBuilderService>
			 * declarationBuilderServiceRegistration = installer
			 * .registerBundleService( felix.getBundleContext(),
			 * DeclarationBuilderService.class, new
			 * DefaultDeclarationBuilderService(felix .getBundleContext()),
			 * null); DeclarationBuilderService service =
			 * felix.getBundleContext() .getService(
			 * declarationBuilderServiceRegistration .getReference());
			 * 
			 * // Context Services //
			 * registerBundleService(IBatteryContextService.class, // new //
			 * BatteryContextService(), null);
			 * 
			 * DeclarationHandle handle = service
			 * .newInstance(IBatteryContextService.class.getName())
			 * .name(BatteryContextService.class.getName())
			 * .context(felix.getBundleContext()) .configure()
			 * .property("service.pid", IBatteryContextService.class.getName())
			 * .build(); handle.publish();
			 */
		} catch (BundleException ex) {
			throw new IllegalStateException(ex);
		}
	}

	private void delete(File target) {
		if (target.isDirectory()) {
			for (File file : target.listFiles()) {
				delete(file);
			}
		}
		target.delete();
	}

	public static void main(String[] args) {
		ApacheFelix apacheFelix = new ApacheFelix();
	}

}
