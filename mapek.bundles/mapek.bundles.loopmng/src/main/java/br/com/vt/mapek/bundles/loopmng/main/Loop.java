package br.com.vt.mapek.bundles.loopmng.main;

import java.util.LinkedList;
import java.util.List;

import br.com.vt.mapek.bundles.loopmng.analyze.Analyzer;
import br.com.vt.mapek.bundles.loopmng.analyze.SymptomRepository;
import br.com.vt.mapek.bundles.loopmng.executor.Executor;
import br.com.vt.mapek.bundles.loopmng.monitor.Monitor;
import br.com.vt.mapek.bundles.loopmng.plan.ChangePlan;
import br.com.vt.mapek.bundles.loopmng.plan.Planner;
import br.com.vt.mapek.services.common.Util;
import br.com.vt.mapek.services.domain.ISensor;

public class Loop {
	private int id;
	private int rate;
	private List<ISensor> sensores;
	private SymptomRepository symptomRepository;
	private ChangePlan changePlan;
	
	public Loop(int rate) {
		this.id = Util.getNewID();
		this.rate = rate;
		this.sensores = new LinkedList<ISensor>();
		this.symptomRepository = new SymptomRepository();
		this.changePlan = new ChangePlan();
	}
	
	public void addSensor(ISensor sensor){
		this.sensores.add(sensor);
	}

	public Loop build(ISensor sensor){
		this.addSensor(sensor);

		// MONITOR
		Monitor monitor = new Monitor();
		sensor.register(monitor);
		
		// ANALYZE
		Analyzer analyzer = new Analyzer(this);
		monitor.register(analyzer);

		// PLANNER
		Planner plan = new Planner(this);
		analyzer.register(plan);

		// EXECUTOR
		Executor executor = new Executor();
		plan.register(executor);
		return this;

	}
	
	public synchronized void run() {
		System.out.println("\n "+this.id+"_____________________________________________________________");
		
		try {
			Thread.sleep(rate);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public List<ISensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<ISensor> sensores) {
		this.sensores = sensores;
	}

	public SymptomRepository getSymptomRepository() {
		return symptomRepository;
	}

	public void setSymptomRepository(SymptomRepository symptomRepository) {
		this.symptomRepository = symptomRepository;
	}

	public ChangePlan getChangePlan() {
		return changePlan;
	}

	public void setChangePlan(ChangePlan changePlan) {
		this.changePlan = changePlan;
	}

	
}
