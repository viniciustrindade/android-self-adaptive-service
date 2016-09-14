package br.com.vt.mapek.services.domain;

public interface IBatterySensor extends ISensor {

	public abstract void readAndNotify();
}
