package br.com.vt.mapek.services;


public interface ISubject {
	public  IObserver register(IObserver observer);
	public  IObserver unregister(IObserver observer);
	public abstract void notifyObservers();
	//public void notifyObservers(Object object);

}
