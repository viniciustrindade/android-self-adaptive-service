package br.com.vt.mapek.android.felix;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import android.app.Activity;
import android.view.View;
import br.com.vt.mapek.felix.view.ViewFactory;

public class FelixTracker<S, T> extends ServiceTracker<S, T> implements
		ServiceTrackerCustomizer<S, T> {
	private Activity activity;

	public FelixTracker(BundleContext context, Filter filter,
			ServiceTrackerCustomizer<S, T> customizer, Activity activity) {
		super(context, filter, null);
		this.activity = activity;

	}

	public T addingService(ServiceReference<S> reference) {
		final T fac = (T) context.getService(reference);

		if (fac instanceof ViewFactory) {
			if (fac != null) {
				final ViewFactory viewFac = (ViewFactory) fac;
				activity.runOnUiThread(new Runnable() {
					public void run() {
						activity.setContentView(viewFac.create(activity));
					}
				});
			}
		}
		return fac;
	}

	public void modifiedService(ServiceReference<S> reference, T service) {

		removedService(reference, service);
		addingService(reference);

	}

	public void removedService(ServiceReference<S> reference, T service) {
		context.ungetService(reference);
		if (service instanceof ViewFactory) {
			activity.runOnUiThread(new Runnable() {
				public void run() {
					activity.setContentView(new View(activity));
				}
			});
		}

	}

	public static Filter getFilterByClass(BundleContext context, Class clazz)
			throws InvalidSyntaxException {
		String filter = "(" + Constants.OBJECTCLASS + "=" + clazz.getName()
				+ ")";

		return context.createFilter(filter);
	}

}
