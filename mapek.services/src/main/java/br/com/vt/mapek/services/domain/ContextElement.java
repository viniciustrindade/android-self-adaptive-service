package br.com.vt.mapek.services.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContextElement {
	private IProperty property;
	private float reading;
	private Date collectionTime;
	
	private ContextElement() {
	}
	
	public ContextElement(IProperty property) {
		this.property = property;
	}

	public IProperty getProperty() {
		return property;
	}

	public void setProperty(IProperty property) {
		this.property = property;
	}

	public float getReading() {
		return reading;
	}

	public void setReading(float reading) {
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