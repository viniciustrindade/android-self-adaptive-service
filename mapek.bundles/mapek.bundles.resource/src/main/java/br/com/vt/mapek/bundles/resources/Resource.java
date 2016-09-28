package br.com.vt.mapek.bundles.resources;

import java.io.InputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.IResource;

@Component
@Instantiate
@Provides
public class Resource implements IResource {

	public static final String LOOP_XML_FILE = "loops.xml";
	
	@Requires
	private IFileService fileManager;
	
	/**
	 * Return a stream of a bundle from resource path
	 * @param filename
	 * @return
	 */
	public InputStream getBundle(String filename){
		return fileManager.getInputStream(filename);
	}
	
	/**
	 * Reads XML File on resource path and convert to XMLLoop Object
	 * @param filename
	 * @return
	 */
	public InputStream getXML(){
		InputStream input = fileManager.getInputStream(LOOP_XML_FILE);
		return input;
	}
	

}
