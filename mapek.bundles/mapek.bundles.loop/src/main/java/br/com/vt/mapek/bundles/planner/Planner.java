package br.com.vt.mapek.bundles.planner;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import br.com.vt.mapek.services.ABObserverSubject;
import br.com.vt.mapek.services.IAdaptationRequest;
import br.com.vt.mapek.services.IChangePlan;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ILoop;
import br.com.vt.mapek.services.IObserver;
import br.com.vt.mapek.services.IPlanner;
import br.com.vt.mapek.services.ISymptomRepository;

@Component
@Provides(strategy = "instance")
@Instantiate
public class Planner extends ABObserverSubject implements IPlanner {

	@Requires
	private ILoggerService log;
	private ILoop loop;

	public Planner() {
		super();
	}

	// Receive AdaptationRequest
	// Send ChangePlan
	public synchronized void update(Object object) {

		log.D(loop + " Planejando \n");

		if (object instanceof IAdaptationRequest) {
			IAdaptationRequest adaptationRequest = (IAdaptationRequest) object;
			ISymptomRepository repo = loop.getSymptomRepository();

			log.D("[Symptom knowed] \t "
					+ repo.getSymptoms().toString() + "\n");
			
			log.D("[Symptom founded] \t "
					+ adaptationRequest.getSymptoms().toString()+ "\n");
			
			// Only make adaptation if have all symptoms
			if (adaptationRequest.getSymptoms().containsAll(repo.getSymptoms())) {
				IChangePlan changePlan = loop.getChangePlan();
				log.D("[notifyObservers(changePlan)] > " + changePlan + "\n");
				notifyObservers(changePlan);
			}

		}

		log.D("");

	}

	public synchronized void notifyObservers(Object object) {
		for (IObserver observer : observers) {
			observer.update(object);
		}

	}

	@Override
	public void setLoop(ILoop loop) {
		this.loop = loop;

	}

}
