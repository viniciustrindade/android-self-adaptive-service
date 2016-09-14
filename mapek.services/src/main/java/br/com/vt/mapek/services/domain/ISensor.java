package br.com.vt.mapek.services.domain;

import java.util.List;

import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.ISubject;

public interface ISensor extends ISubject {

	public int getSensorID();

	public void setSensorID(int sensorID);

	public IObserver register(IObserver observer);

	public IObserver unregister(IObserver observer);

	public void notifyObservers();

	public void notifyObservers(ContextElement contextElement);

	public void readAndNotify();

	public List<IObserver> getObservers();

	public void setObservers(List<IObserver> observers);

	public String toString();

}