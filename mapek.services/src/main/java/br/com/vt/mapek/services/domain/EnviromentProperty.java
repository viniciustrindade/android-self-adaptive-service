package br.com.vt.mapek.services.domain;





public enum EnviromentProperty implements Property{
	LUMINOSIDADE(30,60);
	
	Threshold threshold;
	
	
	EnviromentProperty(int low, int up) {
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
