package br.com.vt.mapek.services;

public interface IPlanner extends IObserver, ISubject {
	public abstract void setLoop(ILoop loop);
}
