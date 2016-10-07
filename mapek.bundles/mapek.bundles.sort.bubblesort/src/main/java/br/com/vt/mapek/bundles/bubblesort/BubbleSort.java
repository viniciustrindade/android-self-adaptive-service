package br.com.vt.mapek.bundles.bubblesort;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;

import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISort;

@Component(immediate=true)
@Instantiate
public class BubbleSort implements ISort, Runnable {
	@Requires
	private IResource res;

	@Requires
	private ILoggerService log;

	private int counter = 0;
	private long spentTime = 0;
	private final static String tmpFileName = "/bubblesort.counter";
	private boolean end = false;

	public void run() {
		log.D("BubbleSort started");

		int[] intArray = res.getArray();

		while (!end) {
			Date before = new Date();
			sort(intArray.clone());
			spentTime += ((new Date()).getTime() - before.getTime());
			res.saveExecution(tmpFileName, counter++, spentTime);
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
