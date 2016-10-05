package br.com.vt.mapek.bundles.quicksort;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISort;

@Component
@Instantiate
public class QuickSort implements ISort {
	@Requires
	private IResource res;
	int a[];
	int counter = 0;
	long spentTime = 0;
	private final static String tmpFileName = "/quicksort.counter";
	private boolean end = false;

	@Validate
	public void start() {
		int[] intArray = res.getArray();

		while (!end) {
			Date before = new Date();
			sort(intArray.clone());
			spentTime += ((new Date()).getTime() - before.getTime());
			res.saveExecution(tmpFileName, counter++, spentTime);
		}

	}

	@Invalidate
	public void stop() {
		end = true;
	}
	
	public void sort(int[] intArray) {
		this.a = intArray;
		sort();

	}

	// This method sorts an array and internally calls quickSort
	public  void sort() {
		int left = 0;
		int right = a.length - 1;

		quickSort(left, right);
	}
	
	// This method is used to swap the values between the two given index
	public  void swap(int left, int right) {
		int temp = a[left];
		a[left] = a[right];
		a[right] = temp;
	}

	public  void printArray() {
		for (int i : a) {
			System.out.print(i + " ");
		}
	}

	// This method is used to partition the given array and returns the integer
	// which points to the sorted pivot index
	private  int partition(int left, int right, int pivot) {
		int leftCursor = left - 1;
		int rightCursor = right;
		while (leftCursor < rightCursor) {
			while (a[++leftCursor] < pivot)
				;
			while (rightCursor > 0 && a[--rightCursor] > pivot)
				;
			if (leftCursor >= rightCursor) {
				break;
			} else {
				swap(leftCursor, rightCursor);
			}
		}
		swap(leftCursor, right);
		return leftCursor;
	}

	// This method is used to sort the array using quicksort algorithm.
	// It takes the left and the right end of the array as the two cursors.
	private  void quickSort(int left, int right) {

		// If both cursor scanned the complete array quicksort exits
		if (left >= right)
			return;

		// For the simplicity, we took the right most item of the array as a
		// pivot
		int pivot = a[right];
		int partition = partition(left, right, pivot);

		// Recursively, calls the quicksort with the different left and right
		// parameters of the sub-array
		quickSort(0, partition - 1);
		quickSort(partition + 1, right);
	}



}
