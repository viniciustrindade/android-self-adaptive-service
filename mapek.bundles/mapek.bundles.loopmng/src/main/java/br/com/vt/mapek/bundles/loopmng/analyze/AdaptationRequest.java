package br.com.vt.mapek.bundles.loopmng.analyze;

import java.util.Date;
import java.util.List;

import br.com.vt.mapek.bundles.loopmng.domain.Symptom;
import br.com.vt.mapek.services.common.Util;

public class AdaptationRequest {
	private int requestID;
	private Date dateOfRequest;
	private List<Symptom> symptoms;
	
	public AdaptationRequest(List<Symptom> symptoms) {
		this.requestID = Util.getNewID();
		this.symptoms = symptoms;
		this.dateOfRequest = new Date();
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public Date getDateOfRequest() {
		return dateOfRequest;
	}
	public void setDateOfRequest(Date dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}
	public List<Symptom> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
