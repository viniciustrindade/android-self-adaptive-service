package br.com.vt.mapek.services.domain;

import java.util.List;

public interface ISystemContextLog {

	public void saveContext(ContextElement state);

	public List<ContextElement> getContexts();
	

	public List<ContextElement> getLast(int number);

}