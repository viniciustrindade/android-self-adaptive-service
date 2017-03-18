package br.com.vt.mapek.android.service;

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
import org.osgi.framework.BundleListener;
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
import android.view.View;
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
	private BundleContext felixBundleContext;
	
	private ServiceTracker tracker;
	private FelixConfig felixConfig;
	private Activity responseActivity;

	public class MapekBinder extends Binder {

		public MapekOSGIService getService(Activity activity) {
			MapekOSGIService.this.responseActivity = activity;

			return MapekOSGIService.this;
		}

	}

	public BundleContext getFelixBundleContext() {
		return felixBundleContext;
	}

	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onStartCommand");
		Toast.makeText(this, "Serviço Mapek Iniciado", Toast.LENGTH_SHORT)
				.show();

		return mBinder;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {

		felixConfig = new FelixConfig();
		felixConfig.configureBundlesDir();
		felixConfig.configureCache();
		Context ct = this.getApplicationContext();
		delete(felixConfig.getCacheDir());
		delete(felixConfig.getBundleDir());
		try {
			List<BundleActivator> activators = new ArrayList<BundleActivator>();
			installer = new Installer(ct);
			activators.add(installer);
			// activators.add(new FileInstall());
			felixConfig.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
					activators);
			felix = new Felix(felixConfig);
			felix.start();
			
			felixBundleContext = felix.getBundleContext();
			// Android Context
			installer.registerBundleService(felixBundleContext,
					Context.class, this.getApplicationContext(),
					new Hashtable<String, String>());
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
		super.onCreate();
	}

	public org.osgi.framework.Bundle[] getInstalledBundles() {
		// Use the system bundle activator to gain external
		// access to the set of installed bundles.
		return installer.getBundles();
	}

	public void addBundleListener(BundleListener listener) {
		felixBundleContext.addBundleListener(listener);
	}

	public void removeBundleListener(BundleListener listener) {
		felixBundleContext.removeBundleListener(listener);
	}

	public void startBundle(long id) {
		try {
			Bundle b = felixBundleContext.getBundle(id);
			b.start();
			Toast.makeText(getApplicationContext(),
					"Bundle " + b.getSymbolicName() + " started ",
					Toast.LENGTH_SHORT).show();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopBundle(long id) {
		try {
			Bundle b = felixBundleContext.getBundle(id);
			b.stop();
			Toast.makeText(getApplicationContext(),
					"Bundle " + b.getSymbolicName() + " stopped ",
					Toast.LENGTH_SHORT).show();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initServiceTracker() {

		try {
		
			org.osgi.framework.Filter filter = FelixTracker.getFilterByClass(
					felixBundleContext, ViewFactory.class);

			tracker = new FelixTracker<ViewFactory, ViewFactory>(felixBundleContext,
					filter, null) {
				public ViewFactory addingService(
						ServiceReference<ViewFactory> reference) {
					final ViewFactory view = (ViewFactory) context
							.getService(reference);
					if (view != null) {
						final ViewFactory viewFac = (ViewFactory) view;
						responseActivity.runOnUiThread(new Runnable() {
							public void run() {
								responseActivity.setContentView(viewFac
										.create(responseActivity));
							}
						});
					}
					return view;

				}

				public void removedService(
						ServiceReference<ViewFactory> reference,
						ViewFactory service) {
					context.ungetService(reference);
					responseActivity.runOnUiThread(new Runnable() {
						public void run() {
							responseActivity.setContentView(new View(
									responseActivity));
						}
					});
				}
			};
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
				.setContentTitle("Mapek").setContentText("Executando..")
				.setSmallIcon(android.R.drawable.alert_light_frame)
				.setContentIntent(pendingIntent).build();
		startForeground(ONGOING_NOTIFICATION_ID, notification);
	}

	public void setResponseActivity(Activity responseActivity) {
		this.responseActivity = responseActivity;
	}

}
