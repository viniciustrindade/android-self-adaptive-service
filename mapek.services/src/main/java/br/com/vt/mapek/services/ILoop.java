package br.com.vt.mapek.services;

import br.com.vt.mapek.services.domain.ISystemContextLog;

public interface ILoop {

	public void run();

	public int getRate();

	public void setRate(int rate);

	public ISymptomRepository getSymptomRepository();

	public void setSymptomRepository(ISymptomRepository symptomRepository);

	public IChangePlan getChangePlan();

	public void setChangePlan(IChangePlan changePlan);

	public ISensor getSensorByClassName(String className);

	public int getId();

	public void setId(int id);

	public ISystemContextLog getSystemContextLog();

	public void setSystemContextLog(ISystemContextLog systemContextLog);

	public IMonitor getMonitor();

	public void setMonitor(IMonitor monitor);

	public ILoggerService getLog();

	public void setLog(ILoggerService log);

	

}