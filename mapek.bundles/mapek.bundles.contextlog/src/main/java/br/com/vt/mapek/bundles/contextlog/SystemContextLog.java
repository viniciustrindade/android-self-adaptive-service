package br.com.vt.mapek.bundles.contextlog;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.ISystemContextLog;

@Component
@Provides(strategy="instance")
@Instantiate
public class SystemContextLog implements ISystemContextLog {
	List<ContextElement> contexts;

	public SystemContextLog() {
		this.contexts = new ArrayList<ContextElement>();
	}

	public synchronized void saveContext(ContextElement context) {
		contexts.add(context);
	}

	public List<ContextElement> getContexts() {
		return contexts;
	}

	public List<ContextElement> getLast(int number) {
		return contexts.subList(contexts.size() - number > 0 ? contexts.size() - number : 0,
				contexts.size());
	}
	
	

}
