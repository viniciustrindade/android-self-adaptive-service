package br.com.vt.mapek.services;

public interface IObserver {
	public void update(Object object);
	public IObserver setSubject(ISubject subject);
}
