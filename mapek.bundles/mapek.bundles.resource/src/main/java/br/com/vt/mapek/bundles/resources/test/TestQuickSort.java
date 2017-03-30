package br.com.vt.mapek.bundles.resources.test;

import java.io.IOException;
import java.util.Date;
import java.util.Stack;

import br.com.vt.mapek.bundles.resources.FileService;
import br.com.vt.mapek.bundles.resources.Resource;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.common.Util;

public class TestQuickSort {

	static int a[];

	public static void main(String[] args) throws IOException {
		IResource resource = new Resource(new FileService(new TestLoggerService()), new TestLoggerService());
		int intArray[] = resource.getArray();
		Integer counter = 0;
		Long spentTimeTotal = 0l;
		Long spentTime = 0l;
		Float level = 0f;
		String tmpFileName = System.getProperty("java.io.tmpdir")
				+  Util.fileDtFormat.format(new Date()) + "_quicksort.counter";

		while (true) {

			a = intArray.clone();
			Date before = new Date();
			// prints the given array
			//System.out.println("\n");
			//System.out.println("DESORDENADO:");
			//printArray();
			
			sort(a);

			//System.out.println("\n");
			//System.out.println("ORDENADO:");
			//printArray();

			spentTime = ((new Date()).getTime() - before.getTime());
			spentTimeTotal += spentTime;
			System.out.println("\n");
			//resource.saveExecution(tmpFileName, counter++, level, spentTime, spentTimeTotal);
			
		}

	}


    /*
     * iterative implementation of quicksort sorting algorithm.
     */
    public static void sort(int[] numbers) {
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
     * Utility method to partition the array into smaller array, and
     * comparing numbers to rearrange them as per quicksort algorithm.
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
     * @param arr - array on which swap will happen
     * @param i
     * @param j
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

	public static void printArray() {
		for (int i : a) {
			System.out.print(i + " ");
		}
	}

}
