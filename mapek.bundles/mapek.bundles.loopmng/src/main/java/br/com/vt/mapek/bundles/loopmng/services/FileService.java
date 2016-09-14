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
	private BundleContext bundleContext;
	

	public FileService() {

		log.D("File Service iniciado");
		this.bundle =  FrameworkUtil.getBundle(this.getClass());
		this.bundleContext = bundle.getBundleContext();
		Thread.currentThread().setContextClassLoader(
				IFileService.class.getClassLoader());
		this.classLoader = Thread.currentThread().getContextClassLoader();

	}

	public InputStream getInputStream(String filename) {
		log.D("[INPUT] filename : " + filename);
		InputStream input = classLoader.getResourceAsStream(filename);
		if (input == null){
			try {
				throw new FileNotFoundException("Recurso nao encontrado!!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return input;

	}

	public OutputStream getOutputStream(String filename) {
		try {
			log.D("[OUTPUT] filename : "
					+ bundle.getClass().getClassLoader().getResource(filename).getFile());
			
			return new FileOutputStream(bundle.getClass().getClassLoader().getResource(filename)
					.getFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Validate
	public void start() {
		log.D("[START] Iniciado File Service");
		
	}

	@Invalidate
	public void stop() {
		log.D("[STOP] Parado File Service");

	}

	@PostRegistration
	public void registered(ServiceReference ref) {
		log.D("Registered id: " + ref.getProperty("service.id"));

	}

	@PostUnregistration
	public void unregistered(ServiceReference ref) {
		log.D("Unregistered by " + ref.getBundle().getSymbolicName());
	}

}
