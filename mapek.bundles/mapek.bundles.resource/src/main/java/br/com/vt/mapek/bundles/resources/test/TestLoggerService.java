package br.com.vt.mapek.bundles.resources.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import br.com.vt.mapek.services.ILoggerService;

public class TestLoggerService implements ILoggerService, LogService {

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

	
	public void logBatteryConsumeExecution(String filename, String csv) {
		try {

			String tmp = System.getProperty("java.io.tmpdir") + File.separator
					+ filename;

			FileWriter out = new FileWriter(tmp, true);
			this.D("[" + tmp + "] " + csv);
			out.write(csv);
			out.flush();
			out.close();
			return;
		} catch (FileNotFoundException e) {
			this.D(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			this.D(e.getMessage());
			e.printStackTrace();
		}

	}
}
