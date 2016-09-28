package br.com.vt.mapek.services;

import java.util.List;

public interface IChangePlan {

	public void addAction(IAction action);

	public List<IAction> getActions();

}