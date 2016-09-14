package br.com.vt.mapek.bundles.loopmng.monitor;
import java.util.concurrent.Semaphore;

public class Semaforo extends Semaphore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Semaforo(int permits) {
		super(permits);

	}

	private static Semaforo instance;

	public static Semaforo getInstance() {
		if (instance == null) {
			instance = new Semaforo(1);
		}
		return instance;
	}
}
