package br.com.vt.mapek.bundles.loopmng.executor;


import java.util.ArrayList;
import java.util.List;

import br.com.vt.mapek.bundles.loopmng.domain.Action;
import br.com.vt.mapek.bundles.loopmng.monitor.SystemStateLog;
import br.com.vt.mapek.bundles.loopmng.plan.ChangePlan;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.ISubject;
import br.com.vt.mapek.services.common.Util;

public class Executor implements IObserver,ISubject{
	private SystemStateLog sysStates;
	private int executorID;
	private List<IObserver> observers;

	public Executor() {
		this.executorID = Util.getNewID();
		this.sysStates = SystemStateLog.getInstance();
		this.observers = new ArrayList<IObserver>();
	}
	
	public void analyze(){
		
	}
	
	

	public SystemStateLog getSysStates() {
		return sysStates;
	}

	public void setSysStates(SystemStateLog sysStates) {
		this.sysStates = sysStates;
	}

	public int getExecutorID() {
		return executorID;
	}

	public void setExecutorID(int executorID) {
		this.executorID = executorID;
	}


	public synchronized void update(Object object) {
		System.out.println("Executando");
		if (object instanceof ChangePlan) {
			ChangePlan changePlan = (ChangePlan) object;
			List<Action>  correctiveActions = changePlan.getCorrectiveActions();
			for (Action action : correctiveActions) {
				action.execute();
			}
		}
		
	}



	public synchronized IObserver setSubject(ISubject subject) {
		subject.register(this);
		return this;
		
	}


	public synchronized  IObserver register(IObserver observer) {
		this.observers.add(observer);
		return observer;
	}


	public synchronized  IObserver unregister(IObserver observer) {
		this.observers.remove(observer);
		return observer;

	}


	public synchronized  void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update(this);
		}

	}



	
	
}
