package br.com.vt.mapek.services;

import br.com.vt.mapek.services.domain.Commands;


public interface IAction {

	public void execute();

	public void startBundle(String name) throws Exception;

	public void stopBundle(String name) throws Exception;

	public void removeBundle(String filename) throws Exception;

	public String getParams();

	public void setParams(String params);
	

	public Commands getCommand();

	public void setCommand(Commands command);

}