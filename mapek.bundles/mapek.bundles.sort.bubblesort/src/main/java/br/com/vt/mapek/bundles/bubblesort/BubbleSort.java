package br.com.vt.mapek.bundles.bubblesort;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.services.IBatterySensor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.ISort;
import br.com.vt.mapek.services.common.Util;

@Component(immediate = true)
@Instantiate
public class BubbleSort implements ISort, Runnable {
	@Requires
	private IResource resource;
	@Requires
	private ILoggerService log;
	private int[] intArray;

	private Integer counter = 0;
	Long spentTimeTotal = 0l;
	Float level = 0f;
	private String tmpFileName = "/" + Util.fileDtFormat.format(new Date())
			+ "_bubblesort.counter";
	private boolean end = false;

	private Bundle bundle;
	private BundleContext context;
	private ISensor batterySensor;

	public BubbleSort() {
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.context = bundle.getBundleContext();
	}

	public void run() {
		log.D("BubbleSort started");
		if (intArray == null) {
			intArray = resource.getArray();
		}
		batterySensor = getSensorByClassName("br.com.vt.mapek.bundles.sensors.battery.IBatterySensor");


		while (!end) {
			if (batterySensor != null)
				level = batterySensor.getCurrentContext().getReading();
			Long spentTime = 0l;
			Date before = new Date();
			sort(intArray.clone());
			spentTime = ((new Date()).getTime() - before.getTime());
			spentTimeTotal += spentTime;
			resource.saveExecution(tmpFileName, counter++, level, spentTime,
					spentTimeTotal);

		}
		log.D("BubbleSort stopped");

	}

	@Validate
	public void start() {
		Thread thread = new Thread(this);
		end = false;
		thread.start();

	}

	@Invalidate
	public void stop() {
		end = true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ISensor getSensorByClassName(String className) {
		ISensor isensor = null;
		try {
			ServiceReference ref = context.getServiceReference(className);
			isensor = (ISensor) context.getService(ref);
			return isensor;

		} catch (Exception e) {
			log.E("Couldnt load sensor class " + className + ", "
					+ e.getMessage() + "\n");
		}
		return isensor;

	}

	public void sort(int[] intArray) {

		/*
		 * In bubble sort, we basically traverse the array from first to
		 * array_length - 1 position and compare the element with the next one.
		 * Element is swapped with the next element if the next element is
		 * greater.
		 * 
		 * Bubble sort steps are as follows.
		 * 
		 * 1. Compare array[0] & array[1] 2. If array[0] > array [1] swap it. 3.
		 * Compare array[1] & array[2] 4. If array[1] > array[2] swap it. ... 5.
		 * Compare array[n-1] & array[n] 6. if [n-1] > array[n] then swap it.
		 * 
		 * After this step we will have largest element at the last index.
		 * 
		 * Repeat the same steps for array[1] to array[n-1]
		 */

		int n = intArray.length;
		int temp = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {

				if (intArray[j - 1] > intArray[j]) {
					// swap the elements!
					temp = intArray[j - 1];
					intArray[j - 1] = intArray[j];
					intArray[j] = temp;
				}

			}
		}

	}



}
