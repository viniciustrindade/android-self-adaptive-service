package br.com.vt.mapek.bundles.loopmng.main;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import br.com.vt.mapek.bundles.loopmng.analyze.Analyzer;
import br.com.vt.mapek.bundles.loopmng.analyze.SymptomRepository;
import br.com.vt.mapek.bundles.loopmng.executor.Executor;
import br.com.vt.mapek.bundles.loopmng.monitor.Monitor;
import br.com.vt.mapek.bundles.loopmng.plan.ChangePlan;
import br.com.vt.mapek.bundles.loopmng.plan.Planner;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.common.Util;

@Component
@Instantiate
@Provides(strategy = "SERVICE")
public class Loop {
	private int id;
	private int rate;
	private SymptomRepository symptomRepository;
	private ChangePlan changePlan;

	private Bundle bundle;
	private BundleContext bundleContext;

	@Requires
	private ILoggerService log;

	@Requires
	private IFileService fileManager;

	public Loop(int rate) {
		this.id = Util.getNewID();
		this.rate = rate;
		this.symptomRepository = new SymptomRepository();
		this.changePlan = new ChangePlan();
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.bundleContext = bundle.getBundleContext();
	}

	public Loop build(ISensor sensor) {
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
		System.out
				.println("\n== LOOP ["
						+ this.id
						+ "]_____________________________________________________________");

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
