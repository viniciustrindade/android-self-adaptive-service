package br.com.vt.mapek.services;

import java.util.List;

import br.com.vt.mapek.services.domain.ContextElement;
import br.com.vt.mapek.services.domain.Symptom;

public interface ISymptomRepository {

	public List<Symptom> search(ContextElement state);

	public List<Symptom> getSymptoms();

	public void addSymptom(Symptom symptom);

}