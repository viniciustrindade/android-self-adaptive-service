package br.com.vt.mapek.bundles.logger;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import br.com.vt.mapek.services.ILoggerService;

@Component
@Instantiate
@Provides
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

	public void logBatteryConsumeExecution(String filename, String title,
			Integer counter, Float level, Long time, Long timeTotal) {

		/*
		 * FileWriter out; try { File tmpFile = new
		 * File(System.getProperty("java.io.tmpdir") + "/" + filename); out =
		 * new FileWriter(tmpFile, true); String csv = counter + "," + level +
		 * "," + time + "," + timeTotal;
		 * 
		 * this.D("[" + tmpFile.getPath() + "] " + csv); out.write(csv);
		 * out.flush(); out.close();
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		/*try {

			int seconds = (int) (timeTotal / 1000);
			int ocsindex = (int) (counter / ((seconds) * (level != 0 ? level
					: 1)));
			// ordering/consume/seconds
			String csv = title + "," + counter + "," + level + "," + time + ","
					+ seconds + "," + ocsindex + "\n";
			File tmp = new File(Environment.getExternalStorageDirectory()
					+ File.separator + filename);
			FileWriter out = new FileWriter(tmp, true);
			out.write(csv);
			out.close();

		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}
}