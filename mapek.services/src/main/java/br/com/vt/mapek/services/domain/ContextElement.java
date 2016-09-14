package br.com.vt.mapek.services.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContextElement {
	private Property property;
	private int reading;
	private Date collectionTime;
	
	private ContextElement() {
	}
	
	public ContextElement(Property property) {
		this.property = property;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public int getReading() {
		return reading;
	}

	public void setReading(int reading) {
		this.reading = reading;
	}

	public Date getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
	}

	@Override
	public String toString() {
		DateFormat format = SimpleDateFormat.getDateInstance();
		
		return "ContextElement [property=" + property.getName() + ", reading=" + reading
				+ ", collectionTime=" + format.format(collectionTime) + "]";
	}
	
	
	
	
}