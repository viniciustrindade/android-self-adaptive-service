package br.com.vt.mapek.services;

import java.util.List;

import br.com.vt.mapek.services.domain.ContextElement;

public interface IMonitor extends IObserver, ISubject {

	public void print(List<ContextElement> list);

	public abstract void setLoop(ILoop loop);

}