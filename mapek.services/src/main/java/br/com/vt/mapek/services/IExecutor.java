package br.com.vt.mapek.services;

public interface IExecutor extends IObserver, ISubject {
	public abstract void setLoop(ILoop loop);
}
