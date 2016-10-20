package br.com.vt.mapek.bundles.executor;

import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import br.com.vt.mapek.services.ABObserverSubject;
import br.com.vt.mapek.services.IAction;
import br.com.vt.mapek.services.IChangePlan;
import br.com.vt.mapek.services.IExecutor;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ILoop;

@Component
@Provides(strategy="instance")
@Instantiate
public class Executor extends ABObserverSubject implements IExecutor {
	@Requires
	private ILoggerService log;

	private ILoop loop;

	public Executor() {
		super();
	}

	// Receive ChangePlan
	// Execute Actions
	public synchronized void update(Object object) {

		log.I(loop + " Executando \n");

		if (object instanceof IChangePlan) {
			IChangePlan changePlan = (IChangePlan) object;
			List<IAction> correctiveActions = changePlan.getActions();
			for (IAction action : correctiveActions) {
				action.execute();
			}
		}

	}

	@Override
	public void setLoop(ILoop loop) {
		this.loop = loop;

	}

}
