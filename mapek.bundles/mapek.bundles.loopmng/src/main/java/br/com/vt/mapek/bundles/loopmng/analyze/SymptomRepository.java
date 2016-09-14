package br.com.vt.mapek.bundles.loopmng.analyze;

import java.util.ArrayList;
import java.util.List;

import br.com.vt.mapek.bundles.loopmng.domain.Condition;
import br.com.vt.mapek.bundles.loopmng.domain.Symptom;
import br.com.vt.mapek.bundles.loopmng.monitor.SystemRuntimeState;
import br.com.vt.mapek.services.domain.Property;
import br.com.vt.mapek.services.domain.Threshold;

public class SymptomRepository {
	List<Symptom> symptoms = new ArrayList<Symptom>();

	public void addSymptom(Property property, Condition condition,
			Threshold threshold) {
		this.symptoms.add(new Symptom(property, condition, threshold));
	}

	public List<Symptom> search(SystemRuntimeState state) {
		List<Symptom> symptomsFounded = new ArrayList<Symptom>();

		for (Symptom symptom : symptoms) {
			if (symptom.check(state.getContext())) {
				symptomsFounded.add(symptom);
			}
		}

		return symptomsFounded;

	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}
}
