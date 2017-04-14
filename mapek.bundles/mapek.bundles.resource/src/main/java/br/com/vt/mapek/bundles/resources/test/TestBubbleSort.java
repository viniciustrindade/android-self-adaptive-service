package br.com.vt.mapek.bundles.resources.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

public class TestBubbleSort extends ABSort {

	static int a[];
	IResource resource;
	ILoggerService log;
	
	public TestBubbleSort() {
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
	
	public void sort(int[] intArray) {
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
		TestBubbleSort test = new TestBubbleSort();
		test.start();

	}
	
	public String getAlgoritmName(){
		return "bubblesort";
	}
	
}
