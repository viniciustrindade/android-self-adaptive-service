package br.com.vt.mapek.services.domain;






public enum SystemProperty implements Property{
	CPU(0,90),
	BATERIA(20,40),
	TEMPERATURA(20,30);
	
	Threshold threshold;
	
	
	SystemProperty(int low, int up) {
		this.threshold = new Threshold(low, up);
	}
	
	
	public String getName(){
		return this.name();
	}

	public Threshold getThreshold() {
		// TODO Auto-generated method stub
		return this.threshold;
	}


	
}
