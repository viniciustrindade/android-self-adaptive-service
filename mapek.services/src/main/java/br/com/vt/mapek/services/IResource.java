package br.com.vt.mapek.services;

import java.io.InputStream;

public interface IResource {

	/**
	 * Return a stream of a bundle from resource path
	 * @param filename
	 * @return
	 */
	public InputStream getBundle(String filename);

	/**
	 * Reads XML File on resource path and convert to XMLLoop Object
	 * @param filename
	 * @return
	 */
	public  InputStream getXML();


	public int[] getArray();

	
	public void saveExecution(String tmpFileName, Integer counter,
			Float batteryLevel, Long spentTime, Long spentTimeTotal);
}