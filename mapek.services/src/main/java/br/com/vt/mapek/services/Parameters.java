package br.com.vt.mapek.services;

import java.io.File;
import java.io.Serializable;

public class Parameters implements IParameters{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1180230900008943204L;
	private File externalDir;
	public File getExternalDir() {
		return externalDir;
	}
	public void setExternalDir(File externalDir) {
		this.externalDir = externalDir;
	}
	
	
	

}
