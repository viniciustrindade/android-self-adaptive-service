package br.com.vt.mapek.services;

public interface IAnalyzer extends IObserver, ISubject {
	public abstract void setLoop(ILoop loop);
}
