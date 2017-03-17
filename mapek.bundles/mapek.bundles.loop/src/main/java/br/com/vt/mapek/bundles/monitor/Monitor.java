package br.com.vt.mapek.bundles.monitor;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import br.com.vt.mapek.services.ABObserverSubject;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ILoop;
import br.com.vt.mapek.services.IMonitor;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.common.Util;
import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.IProperty;

@Component
@Provides(strategy = "instance")
@Instantiate
public class Monitor extends ABObserverSubject implements IMonitor {

	@Requires
	private ILoggerService log;
	private ILoop loop;
	private static final int limit = 1;

	public Monitor() {
		super();
	}

	// Receive ContextElement
	// Send last 10 contexts
	public synchronized void update(Object object) {
		if (object instanceof ContextElement) {
			ContextElement context = (ContextElement) object;
			log.I(loop
					+ " Monitorando ________________________________________________________________________\n");

			loop.getSystemContextLog().saveContext(context);
			Collection<List<ContextElement>> list = loop.getSystemContextLog()
					.getLast(limit);

			print(list);
			log.I("\n");
			notifyObservers(list);
		}

	}

	public synchronized void print(Collection<List<ContextElement>> all) {

		for (List<ContextElement> list : all) {

			if (!list.isEmpty()) {
				ContextElement first = list.get(0);
				IProperty p = first.getProperty();
				String toPrint = "[" + p.getName() + "(" + p.getUnit() + ")]\t";
				for (ContextElement context : list) {
					String date = Util.timeFormat.format(context
							.getCollectionTime());
					String value = String.valueOf(context.getReading());
					toPrint += "[" + date + " " + value + "] ";
				}
				log.I(toPrint);
			}

		}

	}

	public synchronized void notifyObservers(
			Collection<List<ContextElement>> list) {
		for (IObserver observer : observers) {
			observer.update(list);
		}

	}

	@Override
	public void setLoop(ILoop loop) {
		this.loop = loop;

	}

}
