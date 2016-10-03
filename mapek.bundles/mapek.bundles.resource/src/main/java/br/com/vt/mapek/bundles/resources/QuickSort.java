package br.com.vt.mapek.bundles.resources;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class QuickSort {
	
	static int a[];

	public static void main(String[] args) throws IOException {

		File tmpFile = File.createTempFile("quicksort", ".counter");
		Resource res = new Resource(new FileService(new LoggerService()));
		int intArray[] = res.getArray();
		int counter = 0;
		long spentTime = 0;

		while (true) {

			a = intArray.clone();
			Date before = new Date();
			// prints the given array
		//	printArray(a);
			sort();

//			System.out.println("");

			// prints the sorted array
			//printArray(a);

			spentTime += ((new Date()).getTime() - before.getTime());
			res.saveExecution(tmpFile, counter++, spentTime);
		}

	}

	// This method sorts an array and internally calls quickSort
	public static void sort() {
		int left = 0;
		int right = a.length - 1;

		quickSort(left, right);
	}

	// This method is used to sort the array using quicksort algorithm.
	// It takes the left and the right end of the array as the two cursors.
	private static void quickSort(int left, int right) {

		// If both cursor scanned the complete array quicksort exits
		if (left >= right)
			return;

		// For the simplicity, we took the right most item of the array as a
		// pivot
		int pivot = a[right];
		int partition = partition( left, right, pivot);

		// Recursively, calls the quicksort with the different left and right
		// parameters of the sub-array
		quickSort( 0, partition - 1);
		quickSort(partition + 1, right);
	}

	// This method is used to partition the given array and returns the integer
	// which points to the sorted pivot index
	private static int partition(int left, int right, int pivot) {
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

	// This method is used to swap the values between the two given index
	public static void swap(int left, int right) {
		int temp = a[left];
		a[left] = a[right];
		a[right] = temp;
	}

	public static void printArray() {
		for (int i : a) {
			System.out.print(i + " ");
		}
	}

}
