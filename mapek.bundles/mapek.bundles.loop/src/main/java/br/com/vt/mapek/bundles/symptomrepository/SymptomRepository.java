package br.com.vt.mapek.bundles.symptomrepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import br.com.vt.mapek.services.ISymptomRepository;
import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.Symptom;

@Component
@Provides(strategy = "instance")
@Instantiate
public class SymptomRepository implements ISymptomRepository {
	List<Symptom> symptoms = new ArrayList<Symptom>();

	public List<Symptom> search(ContextElement state) {
		List<Symptom> founded = new ArrayList<Symptom>();

		for (Symptom symptom : symptoms) {
			// optimizing: No checking if already have done
			if (!founded.contains(symptom)) {
				if (symptom.check(state)) {
					founded.add(symptom);
				}
			}
		}

		return founded;

	}

	public List<Symptom> search(Collection<List<ContextElement>> states) {
		List<Symptom> founded = new ArrayList<Symptom>();

		for (List<ContextElement> list : states) {
			for (ContextElement state : list) {
				for (Symptom symptom : symptoms) {
					// optimizing: No checking if already have done
					if (!founded.contains(symptom)) {
						if (symptom.check(state)) {
							founded.add(symptom);
						}
					}
				}
			}
		}

		return founded;

	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void addSymptom(Symptom symptom) {
		symptoms.add(symptom);
	}
}
