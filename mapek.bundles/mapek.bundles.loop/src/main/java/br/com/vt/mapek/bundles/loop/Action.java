package br.com.vt.mapek.bundles.loop;

import java.io.InputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

import br.com.vt.mapek.services.IAction;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.domain.Commands;

public class Action implements IAction {
	
	private Commands command;
	
	private String params;

	private IResource resource;

	private ILoggerService log;
	
	private BundleContext context;

	public Action(BundleContext context,IResource resource,ILoggerService log, Commands command, String params) {
		super();
		this.command = command;
		this.params = params;
		this.context = context;
		this.resource = resource;
		this.log = log;
	}

	public void execute() {
		switch (command) {
		case STOP_COMPONENT:

			try {
				stopBundle(this.params);
			} catch (BundleException e) {
				String error = "\nComponente " + params + " não foi parado\n";
			//	log.D(error + ": \n\t" + e.getMessage() + "\n\n");
				return;
			}
			log.I("Componente " + params + " parado \n");
			return;
		case START_COMPONENT:
			try {
				startBundle(this.params);
			} catch (BundleException e) {
				String error = "\nComponente " + params
						+ " não foi instalado\n";
				//log.D(error + ": \n\t" + e.getMessage()+ "\n\n");

				return;
			}

			log.I("Componente " + params + " iniciado\n");
			return;
		case REMOVE_COMPONENT:

			try {
				removeBundle(this.params);
			} catch (BundleException e) {
				String error = "\nComponente " + params
						+ " não foi removido\n";
			//	log.D(error + ": \n\t" + e.getMessage()+ "\n");
				return;
			}

			log.I("Componente " + params + " removido\n");
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
		Bundle bundle = context.getBundle("file:///" + filename);
		if (bundle == null) {
			InputStream input = resource.getBundle(filename);
			bundle = context.installBundle("file:///" + filename, input);
		}

		return bundle;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Commands getCommand() {
		return command;
	}

	public void setCommand(Commands command) {
		this.command = command;
	}
	

}
