package br.com.vt.mapek.bundles.loopmng.plan;
import java.util.HashMap;

import br.com.vt.mapek.bundles.loopmng.domain.Action;
import br.com.vt.mapek.bundles.loopmng.domain.Symptom;


public class PolicyEngine {
	private HashMap<Symptom,Action> rules;
	
	public PolicyEngine() {
		this.rules = new HashMap<Symptom,Action>();		
	}
	public void addRule(Symptom symptom, Action action){
		this.rules.put(symptom, action);
	}
	public Action getAction(Symptom s){
		return this.rules.get(s);
	}
}
