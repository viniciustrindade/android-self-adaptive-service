package br.com.vt.mapek.bundles.resources.test;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import br.com.vt.mapek.services.ILoggerService;

public class LoggerService implements ILoggerService, LogService {


	public void log(int level, String message) {
		console(level, message);

	}

	public void log(int level, String message, Throwable exception) {
		console(level, message);

	}

	public void log(ServiceReference sr, int level, String message) {
		console(level, message);

	}

	public void log(ServiceReference sr, int level, String message,
			Throwable exception) {
		console(level, message);

	}
	
	public void I(String message) {
		log(LOG_INFO, message);
	}

	public void E(String message) {
		log(LOG_ERROR, message);
	}

	public void W(String message) {
		log(LOG_WARNING, message);
	}

	public void D(String message) {
		log(LOG_DEBUG, message);

	}
	public void console(int level, String message) {
		switch (level) {
		case LOG_ERROR:
			System.err.println(message);
			break;
		default:
			System.out.println(message);
			break;
		}

	}
}
