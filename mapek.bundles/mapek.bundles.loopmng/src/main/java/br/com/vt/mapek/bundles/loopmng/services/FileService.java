package br.com.vt.mapek.bundles.loopmng.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.PostRegistration;
import org.apache.felix.ipojo.annotations.PostUnregistration;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;

@Component
@Provides
@Instantiate
public class FileService implements IFileService {

	@Requires
	private ILoggerService log;

	private ClassLoader classLoader;
	private Bundle bundle;

	public FileService() {
		log.D("File Service iniciado");
		this.bundle = FrameworkUtil.getBundle(this.getClass());

	}

	public InputStream getInputStream(String filename) {
		log.D("[INPUT] filename : " + filename);
		InputStream input = null;

		ClassLoader orig = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(
				IFileService.class.getClassLoader());
		try {

			input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(filename);

		} finally {
			Thread.currentThread().setContextClassLoader(orig);
		}

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
		ClassLoader orig = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(
				IFileService.class.getClassLoader());
		try {
			String file = Thread.currentThread().getContextClassLoader()
					.getResource(filename).getFile();
			
			log.D("[OUTPUT] filename : " + file);

			try {
				out = new FileOutputStream(file);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		} finally {
			Thread.currentThread().setContextClassLoader(orig);
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
