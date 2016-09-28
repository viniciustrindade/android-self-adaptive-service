package br.com.vt.mapek.services;

import java.util.List;

import br.com.vt.mapek.services.domain.ContextElement;

public interface ISensor extends ISubject {

	public int getSensorID();

	public void setSensorID(int sensorID);

	public IObserver register(IObserver observer);

	public IObserver unregister(IObserver observer);

	public void notifyObservers();

	public void notifyObservers(ContextElement contextElement);

	public List<IObserver> getObservers();
	
	public void setObservers(List<IObserver> observers);

	public ContextElement getCurrentContext();

	public String toString();

	public void start();

	public void stop();
	
	public void register();
	public void unregister();

}