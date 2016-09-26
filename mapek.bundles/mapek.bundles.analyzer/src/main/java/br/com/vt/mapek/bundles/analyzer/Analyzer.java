package br.com.vt.mapek.bundles.analyzer;

import java.util.Iterator;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import br.com.vt.mapek.services.ABObserverSubject;
import br.com.vt.mapek.services.IAnalyzer;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ILoop;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.ISystemContextLog;
import br.com.vt.mapek.services.domain.Symptom;

@Component
@Provides(strategy="instance")
@Instantiate
public class Analyzer extends ABObserverSubject implements IAnalyzer {

	@Requires
	private ILoggerService log;

	private ILoop loop;

	public Analyzer() {
		super();
	}

	// Receive last 10 logContext
	// Send AdaptationRequest
	public synchronized void update(Object object) {

		log.I(loop + " Analizando \n");
		if (object instanceof List) {
			List<ContextElement> last10 = (List<ContextElement>) object;
			Iterator<ContextElement> it = last10.iterator();
			while (it.hasNext()) {
				ContextElement state = it.next();
				List<Symptom> symthoms = loop.getSymptomRepository().search(
						state);
				if (!symthoms.isEmpty()) {
					notifyObservers(new AdaptationRequest(symthoms));
					it.remove();
				}
			}
			log.I("\n");
		}

	}

	public synchronized void notifyObservers(AdaptationRequest adaptationRequest) {
		for (IObserver observer : observers) {
			observer.update(adaptationRequest);
		}

	}

	@Override
	public void setLoop(ILoop loop) {
		this.loop = loop;

	}

}
