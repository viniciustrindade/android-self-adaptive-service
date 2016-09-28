package br.com.vt.mapek.services;

import java.util.Collection;
import java.util.List;

import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.Symptom;

public interface ISymptomRepository {

	public List<Symptom> search(ContextElement state);
	
	public List<Symptom> search(Collection<List<ContextElement>> states);

	public List<Symptom> getSymptoms();

	public void addSymptom(Symptom symptom);

}