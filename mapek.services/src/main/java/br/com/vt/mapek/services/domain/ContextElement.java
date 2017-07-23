package br.com.vt.mapek.services.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContextElement {
	private IProperty property;
	private float value;
	private Date collectionTime;
	
	@SuppressWarnings("unused")
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

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
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
		
		return "ContextElement [property=" + property.getName() + ", reading=" + value
				+ ", collectionTime=" + format.format(collectionTime) + "]";
	}
	
	
	
	
}