package br.com.vt.mapek.bundles.sensors.battery;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.common.Util;
import br.com.vt.mapek.services.domain.ContextElement;

public abstract class ABSensor extends BroadcastReceiver implements ISensor {
	private int sensorID;
	private List<IObserver> observers;

	public ABSensor() {
		super();
		this.sensorID = Util.getNewID();
		this.observers = new ArrayList<IObserver>();
	}

	public int getSensorID() {
		return sensorID;
	}

	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}

	public synchronized IObserver register(IObserver observer) {
		this.observers.add(observer);
		return observer;
	}

	public synchronized IObserver unregister(IObserver observer) {
		this.observers.remove(observer);
		return observer;

	}

	public synchronized void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update(this);
		}

	}
	

	public synchronized void notifyObservers(ContextElement contextElement) {
		for (IObserver observer : observers) {
			observer.update(contextElement);
		}

	}
	

	public List<IObserver> getObservers() {
		return observers;
	}
	
	public void setObservers(List<IObserver> observers) {
		this.observers = observers;
	}

	@Override
	public synchronized String toString() {

		return "[SENSOR] : " + this.getSensorID();
	}
	
	
}
