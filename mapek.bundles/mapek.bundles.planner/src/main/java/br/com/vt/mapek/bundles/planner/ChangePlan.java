package br.com.vt.mapek.bundles.planner;
import java.util.LinkedList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import br.com.vt.mapek.services.IAction;
import br.com.vt.mapek.services.IChangePlan;
import br.com.vt.mapek.services.domain.Commands;


@Component
@Provides(strategy="instance")
@Instantiate
public class ChangePlan implements IChangePlan {
	private List<IAction> actions;
	
	public ChangePlan() {
		this.actions = new LinkedList<IAction>();
	}
	
	public void addAction(IAction action){
		this.actions.add(action);
	}
	
	public List<IAction> getActions() {
		return actions;
	}
	
	@Override
	public String toString() {
		String retorno = "";
		for (IAction a:actions){
			retorno+= a.getCommand().name() +" "+ a.getParams() + " ";
		}
		return retorno;
	}
}
