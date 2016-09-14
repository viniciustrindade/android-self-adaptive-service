package br.com.vt.mapek.bundles.validator;

import static java.lang.String.format;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Dictionary;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.arch.gogo.Arch;
import org.apache.felix.ipojo.architecture.Architecture;
import org.apache.felix.ipojo.architecture.InstanceDescription;
import org.apache.felix.ipojo.extender.InstanceDeclaration;

import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IValidator;

@Component(immediate = true)
@Provides
@Instantiate
public class Validator extends Arch implements IValidator {

	@Requires
	ILoggerService log;

	@Requires(optional = true)
	private Architecture[] m_archs;

	@Requires(optional = true)
	private InstanceDeclaration[] m_instances;

	@Validate
	public void start() {
		log.I("[START] Iniciado Validator");
		String erros = invalidinstances();
		if (erros != null && erros.length() > 0) {
			log.I("[ERROS NA VALIDACAO] ========================================================");
			log.I(erros);
		}
	}

	public synchronized String getInstances() {
		StringBuilder buffer = new StringBuilder();
		for (Architecture m_arch : m_archs) {
			InstanceDescription instance = m_arch.getInstanceDescription();
			if (instance.getState() == ComponentInstance.VALID) {
				buffer.append(format("Instance %s -> valid%n",
						instance.getName()));
			}
			if (instance.getState() == ComponentInstance.INVALID) {
				buffer.append(format("Instance %s -> invalid%n",
						instance.getName()));
			}
			if (instance.getState() == ComponentInstance.STOPPED) {
				buffer.append(format("Instance %s -> stopped%n",
						instance.getName()));
			}
		}

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

		if (buffer.length() == 0) {
			buffer.append("No instances \n");
		}
		return buffer.toString();
	}

	public synchronized String invalidinstances() {
		StringBuilder buffer = new StringBuilder();
		for (Architecture m_arch : m_archs) {
			InstanceDescription instance = m_arch.getInstanceDescription();
			if (instance.getState() == ComponentInstance.INVALID) {
				buffer.append(format("Instance %s -> invalid%n",
						instance.getName()));
				buffer.append("\n"
						+ getInvalidInstanceDetail(instance.getName()));
			}
			if (instance.getState() == ComponentInstance.STOPPED) {
				buffer.append(format("Instance %s -> stopped%n",
						instance.getName()));
				buffer.append("\n"
						+ getInvalidInstanceDetail(instance.getName()));
			}
		}

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

		if (buffer.length() == 0) {
			buffer.append("No instances \n");
		}
		return buffer.toString();
	}

	public synchronized String getInvalidInstanceDetail(String instance) {

		StringBuilder sb = new StringBuilder();

		for (Architecture m_arch : m_archs) {
			InstanceDescription id = m_arch.getInstanceDescription();
			if (id.getName().equalsIgnoreCase(instance)) {
				sb.append(id.getDescription());
				sb.append('\n');
			}
		}

		for (InstanceDeclaration instanceDeclaration : m_instances) {
			if (!instanceDeclaration.getStatus().isBound()) {
				if (instance
						.equals(name(instanceDeclaration.getConfiguration()))) {
					sb.append(format(
							"InstanceDeclaration %s not bound to its factory%n",
							instance));
					sb.append(format(" type: %s%n",
							instanceDeclaration.getComponentName()));
					sb.append(format(" reason: %s%n", instanceDeclaration
							.getStatus().getMessage()));
					Throwable throwable = instanceDeclaration.getStatus()
							.getThrowable();
					if (throwable != null) {
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						throwable.printStackTrace(new PrintStream(os));
						sb.append(" throwable: ");
						sb.append(os.toString());
					}
				}
			}
		}

		if (sb.length() == 0) {
			return ("Instance named '" + instance + "' not found");
		} else {
			return sb.toString();
		}

	}

	private synchronized String name(Dictionary<String, Object> configuration) {
		String name = (String) configuration.get("instance.name");
		if (name == null) {
			name = "unnamed";
		}
		return name;
	}

}
