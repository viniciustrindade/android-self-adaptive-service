package br.com.vt.mapek.bundles.loopmng.services;

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

/*	@Requires(optional = true)
	private InstanceDeclaration[] m_instances;*/
	
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
		//sstart();
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

	/*private String name(Dictionary<String, Object> configuration) {
		String name = (String) configuration.get("instance.name");
		if (name == null) {
			name = "unnamed";
		}
		return name;
	}

	@Validate
	public void start() {
		StringBuilder buffer = new StringBuilder();
		for (InstanceDeclaration instance : m_instances) {
			// Only print unbound instances (others already printed above)
			if (!instance.getStatus().isBound()) {
				buffer.append(format("Instance %s of type %s is not bound.%n",
						name(instance.getConfiguration()), instance
								.getConfiguration().get("component")));
				buffer.append(format("  Reason: %s", instance.getStatus()
						.getMessage()));
				buffer.append("\n");
			}
		}

		System.out.println(buffer.toString());
	}*/
}
