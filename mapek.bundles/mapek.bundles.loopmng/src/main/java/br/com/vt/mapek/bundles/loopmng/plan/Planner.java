package br.com.vt.mapek.bundles.loopmng.plan;

import java.util.ArrayList;
import java.util.List;

import br.com.vt.mapek.bundles.loopmng.analyze.AdaptationRequest;
import br.com.vt.mapek.bundles.loopmng.analyze.SymptomRepository;
import br.com.vt.mapek.bundles.loopmng.main.Loop;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.ISubject;

public class Planner implements IObserver, ISubject {
	private ChangePlan changePlan;
	private List<IObserver> observers;
	private Loop loop;

	public Planner(Loop loop) {
		super();
		this.observers = new ArrayList<IObserver>();
		this.loop = loop;
	}

	public ChangePlan getChangePlan() {
		return changePlan;
	}

	public void setChangePlan(ChangePlan changePlan) {
		this.changePlan = changePlan;
	}

	public synchronized void update(Object object) {
		System.out.println("Planejando");

		if (object instanceof AdaptationRequest) {
			AdaptationRequest adaptationRequest = (AdaptationRequest) object;
			SymptomRepository repo = loop.getSymptomRepository();
			if (repo.getSymptoms().containsAll(adaptationRequest.getSymptoms())) {
				ChangePlan changePlan = loop.getChangePlan();
				notifyObservers(changePlan);
			}
		}

		System.out.println("");

	}

	public synchronized IObserver setSubject(ISubject subject) {
		subject.register(this);
		return this;

	}

	public synchronized IObserver register(IObserver observer) {
		this.observers.add(observer);
		return observer;
	}

	public synchronized IObserver unregister(IObserver observer) {
		this.observers.remove(observer);
		return observer;

	}

	public synchronized void notifyObservers(Object object) {
		for (IObserver observer : observers) {
			observer.update(object);
		}

	}
	public synchronized void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update(this);
		}

	}

}
