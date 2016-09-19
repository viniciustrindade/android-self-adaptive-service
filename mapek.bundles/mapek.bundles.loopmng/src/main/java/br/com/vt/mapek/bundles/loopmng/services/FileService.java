package br.com.vt.mapek.bundles.loopmng.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;

@Component
@Provides
@Instantiate
public class FileService implements IFileService {

	@Requires
	Context context;
	
	@Requires
	private ILoggerService log;
	private Bundle bundle;

	public FileService() {
		log.D("File Service iniciado");
		this.bundle = FrameworkUtil.getBundle(this.getClass());

	}

	public InputStream getInputStream(String filename) {
		log.D("[INPUT] filename : " + filename);
		InputStream input = null;
		
		ClassLoader classLoader = Persister.class.getClassLoader();

		log.D("filemanager classloader: " + this.getClass().getClassLoader().getClass().getName());
		input = classLoader.getResourceAsStream(filename);

		if (input == null) {
			try {
				throw new FileNotFoundException("Recurso nao encontrado!!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return input;

	}

	public OutputStream getOutputStream(String filename) {

		FileOutputStream out = null;
		ClassLoader classLoader = Persister.class.getClassLoader();
		
		String file = classLoader.getResource(filename).getFile();

		log.D("[OUTPUT] filename : " + file);

		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	@Validate
	public void start() {
		log.D("[START] Iniciado File Service");

	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado File Service");

	}

}
