package br.com.vt.mapek.services;

public interface ILoggerService {

	public void I(String message);

	public void D(String message);

	public void E(String message);

	public void W(String message);
	
	public void logBatteryConsumeExecution(String filename, String csv);

}
