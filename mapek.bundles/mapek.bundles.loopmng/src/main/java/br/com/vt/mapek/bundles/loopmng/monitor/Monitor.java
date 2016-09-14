package br.com.vt.mapek.bundles.loopmng.monitor;


import java.util.ArrayList;
import java.util.List;

import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.ISubject;
import br.com.vt.mapek.services.common.Util;
import br.com.vt.mapek.services.domain.ContextElement;

public class Monitor implements IObserver, ISubject {
	private SystemStateLog sysStates;
	private int monitorID;
	private List<IObserver> observers;


	public Monitor() {
		super();
		this.monitorID = Util.getNewID();
		this.sysStates = SystemStateLog.getInstance();
		this.observers = new ArrayList<IObserver>();
	}

	public SystemStateLog getSysStates() {
		return sysStates;
	}
	
	public int getMonitorID() {
		return monitorID;
	}



	public void setMonitorID(int monitorID) {
		this.monitorID = monitorID;
	}




	public synchronized  IObserver register(IObserver observer) {
		this.observers.add(observer);
		return observer;
	}


	public synchronized  IObserver unregister(IObserver observer) {
		this.observers.remove(observer);
		return observer;

	}


	public synchronized void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update(this.sysStates);
		}

	}


	public synchronized void update(Object object) {
		if(object instanceof ContextElement){
			ContextElement context= (ContextElement) object;
			System.out.println("Monitorando");
			SystemRuntimeState state = new SystemRuntimeState(context);
			this.sysStates.saveState(state);
			this.log(sysStates);
			notifyObservers();
		}
		
	}

	public void log(SystemStateLog log) {
		List<SystemRuntimeState> list = log.getSystemStates();
		list = list.subList(list.size() - 10 > 0 ? list.size() - 10 : 0,
				list.size());
		
		for (SystemRuntimeState state : list) {
			ContextElement context = state.getContext();
			System.out.println(Util.dtFormat.format(context.getCollectionTime()) + " | " + context.getReading() + "\n");
		}

	}

	public synchronized IObserver setSubject(ISubject subject) {
		subject.register(this);
		return this;
		
	}
	
	

}
