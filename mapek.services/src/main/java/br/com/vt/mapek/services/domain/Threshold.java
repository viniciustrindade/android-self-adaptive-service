package br.com.vt.mapek.services.domain;

public class Threshold {
	private float lowerBound;
	private float upperBound;
	private Boolean onceBound = false;
	
	public Threshold(Integer bound, Integer lowerBound, Integer upperBound) {
		super();
		if (lowerBound != null && upperBound!= null){
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
		}else{
			this.lowerBound = bound;
			onceBound = true;
		}
	}
	public Threshold(float lowerBound, float upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	public float getLowerBound() {
		return lowerBound;
	}
	public void setLowerBound(float lowerBound) {
		this.lowerBound = lowerBound;
	}
	public float getUpperBound() {
		return upperBound;
	}
	public void setUpperBound(float upperBound) {
		this.upperBound = upperBound;
	}
	public Boolean getOnceBound() {
		return onceBound;
	}
	public void setOnceBound(Boolean onceBound) {
		this.onceBound = onceBound;
	}
	
	@Override
	public String toString() {

		return " " + String.valueOf(onceBound ? lowerBound : lowerBound + "," + upperBound) + " ";
	}
	
	
}
