package br.com.vt.mapek.bundles.resources.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import br.com.vt.mapek.bundles.resources.FileService;
import br.com.vt.mapek.bundles.resources.Resource;
import br.com.vt.mapek.services.IBatterySensor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.domain.ABSort;
import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.Property;

public class TestQuickSort extends ABSort {

	static int a[];
	IResource resource;
	ILoggerService log;
	
	public TestQuickSort() {
		super();
		log = new TestLoggerService();
		resource = new Resource(new FileService(new TestLoggerService()),
				new TestLoggerService());
		batterySensor = new IBatterySensor() {
			
			@Override
			public void unregister() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public IObserver unregister(IObserver observer) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void stop() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void start() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setSensorID(int sensorID) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setObservers(List<IObserver> observers) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void register() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public IObserver register(IObserver observer) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void notifyObservers(ContextElement contextElement) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void notifyObservers() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getSensorID() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<IObserver> getObservers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ContextElement getCurrentContext() {
				ContextElement ce = new ContextElement(Property.BATTERY);
				ce.setCollectionTime(new Date());
				ce.setValue(getBatteryLevel());
				return ce;
			}
			
			@Override
			public float getBatteryLevel() {
				Random r = new Random();
				int index = (r.nextInt() % 5);
				index = index > 0 ? index :  index * -1;
				int [] values = {15,15,15,15,14,15};
				float value = values[index];
				return value;
			}
		};
	}
	

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

	public void start() {
		Thread thread = new Thread(this);
		end = false;
		thread.start();
	}

	public void stop() {
		end = true;
	}

	public ILoggerService getLog() {
		return this.log;
	};

	public IResource getResource() {
		return this.resource;
	}

	@Override
	public ISensor getSensorByClassName(String className) {
		return batterySensor;
	};

	
	public static void main(String[] args) throws IOException {
		TestQuickSort test = new TestQuickSort();
		test.start();

	}
	
	public String getAlgoritmName(){
		return "quicksort";
	}

}
