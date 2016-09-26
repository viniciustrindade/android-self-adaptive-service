package br.com.vt.mapek.bundles.monitor;

import java.util.List;

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

@Component
@Provides(strategy = "instance")
@Instantiate
public class Monitor extends ABObserverSubject implements IMonitor {

	@Requires
	private ILoggerService log;
	private ILoop loop;

	public Monitor() {
		super();
	}
 
	// Receive ContextElement
	// Send last 10 contexts
	public synchronized void update(Object object) {
		if (object instanceof ContextElement) {
			ContextElement context = (ContextElement) object;
			log.I(loop + " Monitorando _____________________________________________________________\n");

			loop.getSystemContextLog().saveContext(context);
			List<ContextElement> list = loop.getSystemContextLog().getLast(10);
			print(list);
			log.I("\n");
			notifyObservers(list);
		}

	}

	public synchronized void print(List<ContextElement> list) {
		for (ContextElement context : list) {
			log.I("[" + Util.dtFormat.format(context.getCollectionTime()) + " "
					+ context.getReading() + "] ");
		}

	}

	public synchronized void notifyObservers(List<ContextElement> list) {
		for (IObserver observer : observers) {
			observer.update(list);
		}

	}

	@Override
	public void setLoop(ILoop loop) {
		this.loop = loop;

	}

}
