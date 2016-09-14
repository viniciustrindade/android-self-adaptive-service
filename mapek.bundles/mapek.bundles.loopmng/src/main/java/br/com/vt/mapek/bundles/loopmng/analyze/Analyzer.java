package br.com.vt.mapek.bundles.loopmng.analyze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.vt.mapek.bundles.loopmng.domain.Symptom;
import br.com.vt.mapek.bundles.loopmng.main.Loop;
import br.com.vt.mapek.bundles.loopmng.monitor.SystemRuntimeState;
import br.com.vt.mapek.bundles.loopmng.monitor.SystemStateLog;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.ISubject;
import br.com.vt.mapek.services.common.Util;

public class Analyzer implements IObserver, ISubject {
	private int analyzerID;
	private List<IObserver> observers;
	private Loop loop;

	public Analyzer(Loop loop) {
		super();
		this.analyzerID = Util.getNewID();
		this.observers = new ArrayList<IObserver>();
		this.loop = loop;
	}

	public int getAnalyzerID() {
		return analyzerID;
	}

	public void setAnalyzerID(int analyzerID) {
		this.analyzerID = analyzerID;
	}

	public synchronized void update(Object object) {

		System.out.println("\nAnalizando");
		if (object instanceof SystemStateLog) {
			SystemStateLog log = (SystemStateLog) object;
			List<SystemRuntimeState> list = log.getSystemStates();
			list = list.subList(list.size() - 10 > 0 ? list.size() - 10 : 0,
					list.size());
			Iterator<SystemRuntimeState> it = list.iterator();
			while (it.hasNext()) {
				SystemRuntimeState state = it.next();
				List<Symptom> symthoms = loop.getSymptomRepository().search(state);
				if (!symthoms.isEmpty()) {
					notifyObservers(new AdaptationRequest(symthoms));
					it.remove();
				}
			}

		/*	for (SystemRuntimeState state : list) {
				List<Symptom> symthoms = repo.search(state);
				if (!symthoms.isEmpty()) {
					notifyObservers(new AdaptationRequest(symthoms));
				}
			}*/

			System.out.println("\n");
		}

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

	public synchronized void notifyObservers(AdaptationRequest adaptationRequest) {
		for (IObserver observer : observers) {
			observer.update(adaptationRequest);
		}

	}

	public synchronized IObserver setSubject(ISubject subject) {
		subject.register(this);
		return this;

	}

}
