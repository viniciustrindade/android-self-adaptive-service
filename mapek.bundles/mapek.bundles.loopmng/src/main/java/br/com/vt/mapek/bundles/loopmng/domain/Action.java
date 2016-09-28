package br.com.vt.mapek.bundles.loopmng.domain;

import java.io.InputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import br.com.vt.mapek.services.IFileService;

public class Action {

	private Commands command;
	private String params;
	private BundleContext bundleContext;

	private IFileService fileManager;

	public Action(Commands command, String params) {
		super();
		this.command = command;
		this.params = params;

	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public BundleContext getBundleContext() {
		return bundleContext;
	}

	public IFileService getFileManager() {
		return fileManager;
	}

	public void setFileManager(IFileService fileManager) {
		this.fileManager = fileManager;
	}

	public void execute() {
		switch (command) {
		case STOP_COMPONENT:

			try {
				stopBundle(this.params);
			} catch (BundleException e) {
				System.out.println("ERRO: Componente " + params
						+ " não foi parado, " + e.getMessage());
				return;
			}

			System.out.println("Componente " + params + " parado");
			return;
		case START_COMPONENT:
			try {
				startBundle(this.params);
			} catch (BundleException e) {
				System.out.println("ERRO: Componente " + params
						+ " não foi instalado, " + e.getMessage());

				return;
			}

			System.out.println("Componente " + params + " iniciado");
			return;
		}
	}

	public void startBundle(String name) throws BundleException {
		Bundle bundle = getBundle(name);
		if (bundle.getState() != Bundle.ACTIVE) {
			bundle.start();
		}
	}

	public void stopBundle(String name) throws BundleException {
		Bundle bundle = getBundle(name);
		if (bundle.getState() == Bundle.ACTIVE) {
			bundle.stop();
		}

	}

	public void removeBundle(String filename) throws BundleException {
		Bundle bundle = getBundle("file:///" + filename);
		if (bundle.getState() == Bundle.ACTIVE) {
			bundle.uninstall();
		}

	}

	public Bundle getBundle(String filename) throws BundleException {
		Bundle bundle = bundleContext.getBundle("file:///" + filename);
		if (bundle == null) {
			System.out.println("filemanager classloader antes: " + fileManager.getClass().getClassLoader().getClass().getName());
			InputStream input = fileManager.getInputStream(filename);
			bundle = bundleContext.installBundle("file:///" + filename, input);
		}

		return bundle;
	}
}
