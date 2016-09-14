package br.com.vt.mapek.services.domain;

public class Threshold {
	private int lowerBound;
	private int upperBound;
	private Boolean onceBound = false;
	
	public Threshold(int bound) {
		super();
		this.lowerBound = bound;
		onceBound = true;
	}
	public Threshold(int lowerBound, int upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	public int getLowerBound() {
		return lowerBound;
	}
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	public int getUpperBound() {
		return upperBound;
	}
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
	public Boolean getOnceBound() {
		return onceBound;
	}
	public void setOnceBound(Boolean onceBound) {
		this.onceBound = onceBound;
	}
	
	
	
	
}
