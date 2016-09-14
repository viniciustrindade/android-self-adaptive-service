package br.com.vt.mapek.bundles.loopmng.plan;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.BundleContext;

import br.com.vt.mapek.bundles.loopmng.domain.Action;
import br.com.vt.mapek.bundles.loopmng.domain.Commands;
import br.com.vt.mapek.services.IFileService;


public class ChangePlan {
	private List<Action> correctiveActions;
	
	public ChangePlan() {
		this.correctiveActions = new LinkedList<Action>();
	}
	public void addAction(BundleContext bundleContext,IFileService fileManager, String actionCommandId, String params){
		Action action = new Action(Commands.valueOf(actionCommandId),params);
		action.setBundleContext(bundleContext);
		action.setFileManager(fileManager);
		this.correctiveActions.add(action);
	}
	
	public List<Action> getCorrectiveActions() {
		return correctiveActions;
	}
}
