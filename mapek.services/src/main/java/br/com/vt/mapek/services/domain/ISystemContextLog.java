package br.com.vt.mapek.services.domain;

import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public interface ISystemContextLog {

	public void saveContext(ContextElement state);

	public TreeMap<IProperty, Stack<ContextElement>> getContexts();
	

	public Collection<List<ContextElement>> getLast(int number);

}