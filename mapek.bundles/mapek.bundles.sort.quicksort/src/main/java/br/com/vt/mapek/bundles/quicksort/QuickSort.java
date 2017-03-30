package br.com.vt.mapek.bundles.quicksort;

import java.util.Date;
import java.util.Stack;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.ISort;

@Component(immediate = true)
@Instantiate
public class QuickSort implements ISort, Runnable {
	@Requires
	private IResource resource;
	@Requires
	private ILoggerService log;
	private int[] intArray;
	Integer counter = 0;
	Long timeTotal = 0l;
	Float levelBefore = 0f;
	private boolean end = false;
	private Bundle bundle;
	private BundleContext context;
	private ISensor batterySensor;

	public QuickSort() {
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.context = bundle.getBundleContext();
	}

	public void run() {

		log.D("QuickSort started");

		if (intArray == null) {
			intArray = resource.getArray();
		}
		batterySensor = getSensorByClassName("br.com.vt.mapek.bundles.sensors.battery.IBatterySensor");
		if (batterySensor != null) {
			levelBefore = batterySensor.getCurrentContext().getReading();
		}

		while (!end) {
			Long time = 0l;
			Date before = new Date();
			Float level = 0f;
			if (batterySensor != null)
				level = levelBefore
						- batterySensor.getCurrentContext().getReading();

			sort(intArray.clone());
			time = ((new Date()).getTime() - before.getTime());
			timeTotal += time;
			log.D("[quicksort] " + counter);
			log.logBatteryConsumeExecution(filename, "quicksort",
					counter++, level, time, timeTotal);
		}
		log.D("QuickSort stopped");

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

	/*
	 * iterative implementation of quicksort sorting algorithm.
	 */
	public void sort(int[] numbers) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		stack.push(numbers.length);

		while (!stack.isEmpty()) {
			int end = stack.pop();
			int start = stack.pop();
			if (end - start < 2) {
				continue;
			}
			int p = start + ((end - start) / 2);
			p = partition(numbers, p, start, end);

			stack.push(p + 1);
			stack.push(end);

			stack.push(start);
			stack.push(p);

		}
	}

	/*
	 * Utility method to partition the array into smaller array, and comparing
	 * numbers to rearrange them as per quicksort algorithm.
	 */
	private static int partition(int[] input, int position, int start, int end) {
		int l = start;
		int h = end - 2;
		int piv = input[position];
		swap(input, position, end - 1);

		while (l < h) {
			if (input[l] < piv) {
				l++;
			} else if (input[h] >= piv) {
				h--;
			} else {
				swap(input, l, h);
			}
		}
		int idx = h;
		if (input[h] < piv) {
			idx++;
		}
		swap(input, end - 1, idx);
		return idx;
	}

	/**
	 * Utility method to swap two numbers in given array
	 *
	 * @param arr
	 *            - array on which swap will happen
	 * @param i
	 * @param j
	 */
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
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

}