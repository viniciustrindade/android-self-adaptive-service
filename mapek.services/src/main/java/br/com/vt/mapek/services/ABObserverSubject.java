package br.com.vt.mapek.services;

import java.util.ArrayList;
import java.util.List;

import br.com.vt.mapek.services.common.Util;

public abstract class ABObserverSubject implements IObserver, ISubject {
	
	int id;
	protected List<IObserver> observers;

	public ABObserverSubject() {
		this.id = Util.getNewID();
		this.observers = new ArrayList<IObserver>();
	}

	public synchronized IObserver register(IObserver observer) {
		this.observers.add(observer);
		return observer;
	}

	public synchronized IObserver unregister(IObserver observer) {
		this.observers.remove(observer);
		return observer;

	}

	public synchronized IObserver setSubject(ISubject subject) {
		subject.register(this);
		return this;

	}
	
	public synchronized void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update(this);
		}

	}
	
	public abstract void setLoop(ILoop loop);
}
