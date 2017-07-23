package br.com.vt.mapek.android.felix;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public abstract class FelixTracker<S, T> extends ServiceTracker<S, T> implements
		ServiceTrackerCustomizer<S, T> {


	public FelixTracker(BundleContext context, Filter filter,
			ServiceTrackerCustomizer<S, T> customizer) {
		super(context, filter, null);
	}



	public abstract T addingService(ServiceReference<S> reference);

	public void modifiedService(ServiceReference<S> reference, T service) {

		removedService(reference, service);
		addingService(reference);

	}

	public abstract void removedService(ServiceReference<S> reference, T service);

	public static Filter getFilterByClass(BundleContext context, @SuppressWarnings("rawtypes") Class clazz)
			throws InvalidSyntaxException {
		String filter = "(" + Constants.OBJECTCLASS + "=" + clazz.getName()
				+ ")";

		return context.createFilter(filter);
	}

}
