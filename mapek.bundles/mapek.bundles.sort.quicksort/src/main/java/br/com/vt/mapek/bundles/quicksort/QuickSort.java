package br.com.vt.mapek.bundles.quicksort;

import java.util.Date;
import java.util.Stack;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import br.com.vt.mapek.services.IBatterySensor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISort;
import br.com.vt.mapek.services.common.Util;

@Component(immediate = true)
@Instantiate
public class QuickSort implements ISort, Runnable {
	@Requires
	private IResource resource;
	/*
	 * @Requires private IBatterySensor batterySensor;
	 */
	@Requires
	private ILoggerService log;
	private int[] intArray;
	Integer counter = 0;
	Long spentTimeTotal = 0l;
	private String tmpFileName = "/" + Util.fileDtFormat.format(new Date())
			+ "_quicksort.counter";
	private boolean end = false;

	public void run() {
		log.D("QuickSort started");

		if (intArray == null) {
			intArray = resource.getArray();
		}

		while (!end) {
			Long spentTime = 0l;
			Float level = 0f;
			Date before = new Date();
			sort(intArray.clone());
			spentTime = ((new Date()).getTime() - before.getTime());
			spentTimeTotal += spentTime;
			resource.saveExecution(tmpFileName, counter++, level, spentTime,
					spentTimeTotal);
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

}
