package br.com.vt.mapek.bundles.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;

@Component
@Instantiate
@Provides
public class Resource implements IResource {

	public static final String LOOP_XML_FILE = "loops.xml";

	@Requires
	private IFileService fileManager;
	
	@Requires
	private ILoggerService log;

	public Resource() {
	}

	public Resource(IFileService fileManager) {
		this.fileManager = fileManager;
	}

	/**
	 * Return a stream of a bundle from resource path
	 * 
	 * @param filename
	 * @return
	 */
	public InputStream getBundle(String filename) {
		return fileManager.getInputStream(filename);
	}

	/**
	 * Reads XML File on resource path and convert to XMLLoop Object
	 * 
	 * @param filename
	 * @return
	 */
	public InputStream getXML() {
		InputStream input = fileManager.getInputStream(LOOP_XML_FILE);
		return input;
	}

	public int[] getArray() {
		// READS
		InputStream in = fileManager.getInputStream("input.csv");
		BufferedReader txt = new BufferedReader(new InputStreamReader(in));
		String toArray = "";
		try {
			while (txt.ready()) {
				toArray = toArray + txt.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// CONVERT
		String[] stringArray = toArray.split(",");
		int intArray[] = new int[stringArray.length];

		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = Integer.valueOf(stringArray[i]);
		}
		return intArray;
	}

	public void saveExecution(String tmpFileName, int counter, long spentTime) {

		PrintWriter out;
		try {
			File tmpFile = new File(System.getProperty("java.io.tmpdir") + tmpFileName);
			out = new PrintWriter(tmpFile);
			log.D("[" +tmpFile.getPath() + "] " + counter + "," + spentTime);
			out.write(counter + "," + spentTime);
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
