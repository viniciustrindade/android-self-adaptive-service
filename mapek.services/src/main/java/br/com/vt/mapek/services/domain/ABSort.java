package br.com.vt.mapek.services.domain;

import java.util.Date;

import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.ISensor;
import br.com.vt.mapek.services.ISort;
import br.com.vt.mapek.services.common.Util;

public abstract class ABSort implements ISort, Runnable {

	protected int[] intArray;
	protected float levelb = 0f;
	protected boolean end = false;
	protected ISensor batterySensor;
	protected static final Float MAX_BATTERY = 15f;
	protected static final Float MIN_BATTERY = 5f;
	protected final String filename = "result.csv";
	protected Long totalTime = 0l;
	protected Integer totalCicle = 0;
	protected Float levela = 0f;
	protected Integer fase = 0;
	protected float deltaLevel = 0f;
	protected float printLevel = 0f;
	protected ILoggerService log;
	
	public ABSort() {
		super();
	}

	public void run() {
		batterySensor = getSensorByClassName("br.com.vt.mapek.bundles.sensors.battery.IBatterySensor");
		if (batterySensor == null) {
			try {
				throw new Exception("Battery Sensor cannot be null");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.stop();
			}
		}

		if (intArray == null) {
			intArray = getResource().getArray();
		}

		levela = batterySensor.getCurrentContext().getValue();
		log.logBatteryConsumeExecution(filename,"\n");
		log.logBatteryConsumeExecution(filename,
				"date,fase,level,algoritm,ordering,time,ocs\n");
		do {
			levelb = batterySensor.getCurrentContext().getValue();
			deltaLevel = levela >= levelb ? levela - levelb : levelb - levela;
			Date before = new Date();
			sort(intArray.clone());
			totalTime += ((new Date()).getTime() - before.getTime());
			totalCicle++;
			if (deltaLevel != printLevel) {	
				logToFile();
				printLevel = deltaLevel;
			}
		} while (!end);

	}
	
	public void  logToFile(){
		fase++;
		String now = Util.fileDtFormat.format(new Date());
		float ocs = (float) (totalCicle / ((totalTime) * (deltaLevel))) * 1000;
		String csv = now + "," + (fase) + "," + printLevel + "," + getAlgoritmName()
				+ "," + totalCicle + "," + (int) (totalTime / 1000) + "," + ocs
				+ "\n";
		log.logBatteryConsumeExecution(filename, csv);
	}

	public abstract void sort(int[] numbers);

	public abstract void start();

	public abstract void stop();

	public abstract String getAlgoritmName();

	public abstract IResource getResource();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public abstract ISensor getSensorByClassName(String className);

}