package br.com.vt.mapek.bundles.resources.test;

import java.io.IOException;
import java.util.Date;

import br.com.vt.mapek.bundles.resources.FileService;
import br.com.vt.mapek.bundles.resources.Resource;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.common.Util;

/*
 Java Bubble Sort Example
 This Java bubble sort example shows how to sort an array of int using bubble
 sort algorithm. Bubble sort is the simplest sorting algorithm.
 */

public class TestBubbleSort {

	public static void main(String[] args) throws IOException {
		IResource resource = new Resource(new FileService(new TestLoggerService()),new TestLoggerService());
		int intArray[] = resource.getArray();
		Integer counter = 0;
		Long spentTimeTotal = 0l;
		Long spentTime = 0l;
		Float level = 0f;
		String tmpFileName = System.getProperty("java.io.tmpdir")
				+  Util.fileDtFormat.format(new Date()) + "_bubblesort.counter";

		while (true) {
			int a[] = intArray.clone();
			Date before = new Date();
			
			// prints the given array
			//System.out.println("DESORDENADO:");
			//printArray(a);
			
			sort(a);
			
			//System.out.println("\nORDENADO:");
			//printArray(a);

			spentTime = ((new Date()).getTime() - before.getTime());
			spentTimeTotal += spentTime;
			resource.saveExecution(tmpFileName, counter++, level, spentTime, spentTimeTotal);
			System.out.println("\n");

		}

	}

	private static void sort(int[] intArray) {

		
		 /* In bubble sort, we basically traverse the array from first to
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
	public static void printArray(int[] a) {
		for (int i : a) {
			System.out.print(i + " ");
		}
	}

}