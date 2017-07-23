package br.com.vt.mapek.bundles.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IParameters;

@Component(immediate =true)
@Instantiate
@Provides
public class LoggerService implements ILoggerService, LogService {

	@Requires
	IParameters para;

	public void log(int level, String message) {
		console(level, message);

	}

	public void log(int level, String message, Throwable exception) {
		console(level, message);

	}

	public void log(@SuppressWarnings("rawtypes") ServiceReference sr, int level, String message) {
		console(level, message);

	}

	public void log(@SuppressWarnings("rawtypes") ServiceReference sr, int level, String message,
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

	public void logBatteryConsumeExecution(String filename,String csv) {

		/*
		 * FileWriter out; try { File tmpFile = new
		 * File(System.getProperty("java.io.tmpdir") + "/" + filename); out =
		 * new FileWriter(tmpFile, true); String csv = counter + "," + level +
		 * "," + time + "," + timeTotal;
		 * 
		 * this.D("[" + tmpFile.getPath() + " - title] " + csv); out.write(csv);
		 * out.flush(); out.close();
		 * 
		 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		try {
		
			
			File tmp = new File("/storage/sdcard/result1.csv");
			if (para != null && para.getExternalDir() != null) {
				tmp = new File(para.getExternalDir().getPath() + "/" + filename);
			}
			FileWriter out = new FileWriter(tmp, true);
			this.D("[" + tmp.getPath() + "] " + csv);
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
		
		/*try {

			File tmp = new File(System.getProperty("java.io.tmpdir") + "/" + filename);
			int seconds = (int) (timeTotal / 1000);
			int ocsindex = (int) (counter / ((seconds) * (level != 0 ? level
					: 1))); //
			// ordering/consume/seconds
			String csv = title + "," + counter + "," + level + "," + time + ","
					+ seconds + "," + ocsindex + "\n";

			if (para != null && para.getExternalDir() != null) {
				tmp = new File(para.getExternalDir().getPath() + "/" + filename);
			}
			FileWriter out = new FileWriter(tmp, true);
			this.D("[" + tmp.getPath() + "] " + csv);
			out.write(csv);
			out.close();
			return;

		} catch (FileNotFoundException e) {
			this.D(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			this.D(e.getMessage());
			e.printStackTrace();
		}
*/
	}
}
