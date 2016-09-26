package br.com.vt.mapek.services;

import java.util.Date;
import java.util.List;

import br.com.vt.mapek.services.domain.Symptom;

public interface IAdaptationRequest {

	public int getRequestID();

	public void setRequestID(int requestID);

	public Date getDateOfRequest();

	public void setDateOfRequest(Date dateOfRequest);

	public List<Symptom> getSymptoms();

	public void setSymptoms(List<Symptom> symptoms);

}