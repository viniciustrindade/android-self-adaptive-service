package br.com.vt.mapek.bundles.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import br.com.vt.mapek.services.IFileService;

/*
 Java Bubble Sort Example
 This Java bubble sort example shows how to sort an array of int using bubble
 sort algorithm. Bubble sort is the simplest sorting algorithm.
 */

public class BubbleSort {

	public static void main(String[] args) throws IOException {
		File tmpFile = new File(System.getProperty("java.io.tmpdir") + "/bubblesort.counter");
	
		Resource res = new Resource(new FileService(new LoggerService()));
		int intArray[] = res.getArray();
		int counter = 0;
		long spentTime = 0;
		
		while (true) {
			int intArrayS[] = intArray.clone();
			Date before = new Date();
			sort(intArrayS);
			spentTime += ((new Date()).getTime() - before.getTime());
			res.saveExecution(tmpFile, counter++, spentTime);
			
		}

	}

	private static void sort(int[] intArray) {

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
