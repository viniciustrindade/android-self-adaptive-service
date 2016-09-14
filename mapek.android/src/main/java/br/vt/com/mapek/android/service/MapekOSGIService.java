package br.vt.com.mapek.android.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import br.com.vt.mapek.android.felix.FelixConfig;
import br.com.vt.mapek.android.felix.FelixTracker;
import br.com.vt.mapek.android.felix.Installer;
import br.com.vt.mapek.felix.view.ViewFactory;

public class MapekOSGIService extends Service {

	private static final String TAG = "MapekContextService";
	private final IBinder mBinder = new MapekBinder();
	private static final int ONGOING_NOTIFICATION_ID = 1;
	private Installer installer = null;
	private Felix felix;
	private ServiceTracker tracker;
	private FelixConfig felixConfig;
	private Activity responseActivity;

	public class MapekBinder extends Binder {

		public MapekOSGIService getService(Activity activity) {
			MapekOSGIService.this.responseActivity = activity;

			return MapekOSGIService.this;
		}

	}

	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onStartCommand");
		Toast.makeText(this, "Serviço Mapek Iniciado", Toast.LENGTH_SHORT)
				.show();

		return mBinder;
	}

	@Override
	public void onCreate() {
		felixConfig = new FelixConfig();
		felixConfig.configureBundlesDir();
		felixConfig.configureCache();
		Context ct = this.getApplicationContext();
		delete(felixConfig.getCacheDir());
		try {
			List<BundleActivator> activators = new ArrayList<BundleActivator>();
			installer = new Installer(ct);
			activators.add(installer);
			// activators.add(new FileInstall());
			felixConfig.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
					activators);
			felix = new Felix(felixConfig);
			felix.start();

			// Android Context
			 installer.registerBundleService(felix.getBundleContext(),
			 Context.class, this.getApplicationContext(), new
			 Hashtable<String, String>());
			 /*

			ServiceRegistration<DeclarationBuilderService> declarationBuilderServiceRegistration = installer
					.registerBundleService(
							felix.getBundleContext(),
							DeclarationBuilderService.class,
							new DefaultDeclarationBuilderService(felix
									.getBundleContext()), null);
			DeclarationBuilderService service = felix.getBundleContext()
					.getService(
							declarationBuilderServiceRegistration
									.getReference());

			// Context Services
			// registerBundleService(IBatteryContextService.class, // new
			// BatteryContextService(), null);

			DeclarationHandle handle = service
					.newInstance(IBatteryContextService.class.getName())
					.name(BatteryContextService.class.getName())
					.context(felix.getBundleContext())
					.configure()
					.property("service.pid",
							IBatteryContextService.class.getName())
					.build();
			handle.publish();
*/
		} catch (BundleException ex) {
			throw new IllegalStateException(ex);
		}
		super.onCreate();
	}

	public org.osgi.framework.Bundle[] getInstalledBundles() {
		// Use the system bundle activator to gain external
		// access to the set of installed bundles.
		return installer.getBundles();
	}

	public void printBundleState() {

		StringBuffer services = new StringBuffer();
		services.append("[SERVICES] \n");

		Bundle[] bundles = getInstalledBundles();
		String teststr = "Bundles Status: \n";
		for (Bundle b : bundles) {

			String stateStr = "";
			StringBuffer servicesInUse = new StringBuffer();
			servicesInUse.append("\n[USING] \n");
			int state = b.getState();

			switch (state) {

			case Bundle.ACTIVE:
				stateStr = "ACTIVE";
				break;
			case Bundle.INSTALLED:
				stateStr = "INSTALLED";
				break;
			case Bundle.RESOLVED:
				stateStr = "RESOLVED";
				break;
			case Bundle.STARTING:
				stateStr = "STARTING";
				break;
			case Bundle.STOPPING:
				stateStr = "STOPPING";
				break;
			case Bundle.UNINSTALLED:
				stateStr = "UNINSTALLED";
				break;
			default:
				stateStr = "UNKNOWN STATE";

			}
			if (stateStr.length() == 0)
				stateStr = "UNKNOWN STATE";

			teststr = teststr + "\n[" + stateStr + "] " + b.getSymbolicName()
					+ " (" + b.getVersion() + ")";

			if (b.getServicesInUse() != null) {
				for (ServiceReference ref : b.getServicesInUse()) {

					for (String id : (String[]) ref.getProperty("objectClass")) {
						servicesInUse
								.append("[")
								.append(ref.getProperty("service.id"))
								.append("]  ")
								.append("[")
								.append(ref.getBundle() != null ? ref
										.getBundle().getSymbolicName() : "")
								.append("] ")
								.append(id)
								.append(" (")
								.append(b.getBundleContext().getService(ref)
										.getClass().getName()).append(") \n");

					}
				}
			}
			teststr += servicesInUse;

		}

		if (felix.getRegisteredServices() != null) {
			for (ServiceReference ref : felix.getRegisteredServices()) {

				for (String id : (String[]) ref.getProperty("objectClass")) {
					services.append("[")
							.append(ref.getProperty("service.id"))
							.append("]  ")
							.append(id)
							.append(" (")
							.append(felix.getBundleContext().getService(ref)
									.getClass().getName()).append(") \n");

				}
			}
		}

		TextView tv = new TextView(this);

		System.out.println(teststr);
		System.out.println(services);

		tv.setText(teststr);

		responseActivity.setContentView(tv);

	}

	public void initServiceTracker() {

		try {
			BundleContext context = felix.getBundleContext();
			org.osgi.framework.Filter filter = FelixTracker.getFilterByClass(
					context, ViewFactory.class);
			tracker = new FelixTracker<ViewFactory, ViewFactory>(context,
					filter, null, responseActivity);
			tracker.open();
		} catch (InvalidSyntaxException e) {

			e.printStackTrace();
		}
	}

	public void onDestroy() {
		super.onDestroy();

		tracker.close();
		tracker = null;
		try {
			felix.stop();
		} catch (BundleException e) {

			e.printStackTrace();
		}
		felix = null;

		delete(felixConfig.getCacheDir());
		felixConfig = null;

	}

	private void delete(File target) {
		if (target.isDirectory()) {
			for (File file : target.listFiles()) {
				delete(file);
			}
		}
		target.delete();
	}

	public void notificar() {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, responseActivity.getClass()), 0);

		Notification notification = new Notification.Builder(this)
				.setContentTitle("Mapek").setContentText("Rodando..")
				.setSmallIcon(android.R.drawable.alert_light_frame)
				.setContentIntent(pendingIntent).build();
		startForeground(ONGOING_NOTIFICATION_ID, notification);
	}

	public void setResponseActivity(Activity responseActivity) {
		this.responseActivity = responseActivity;
	}

}
