package br.com.vt.mapek.bundles.contextlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.IProperty;
import br.com.vt.mapek.services.domain.ISystemContextLog;

@Component
@Provides(strategy = "instance")
@Instantiate
public class SystemContextLog implements ISystemContextLog {

	private TreeMap<IProperty, Stack<ContextElement>> contexts;

	public SystemContextLog() {

		this.contexts = new TreeMap<IProperty, Stack<ContextElement>>();

	}

	public synchronized void saveContext(ContextElement context) {
		IProperty p = context.getProperty();
		if (contexts.get(p) == null)
			contexts.put(p, new Stack<ContextElement>());

		contexts.get(p).push(context);
	}

	public TreeMap<IProperty, Stack<ContextElement>> getContexts() {
		return contexts;
	}
 
	public Collection<List<ContextElement>> getLast(int number) {
		Collection<List<ContextElement>> newList = new  ArrayList<List<ContextElement>>();
		
		for (Stack<ContextElement> list: contexts.values()){
			int size = list.size();
			List<ContextElement> last = list.subList(size - number > 0 ? size - number : 0, size);
			newList.add(last);
		}

		
		return newList;
	}

}
