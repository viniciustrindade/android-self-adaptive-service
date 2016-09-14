package br.com.vt.mapek.bundles.loopmng.monitor;

import java.util.ArrayList;
import java.util.List;

public class SystemStateLog {
	List<SystemRuntimeState> systemStates;

	private static SystemStateLog instance;

	public static SystemStateLog getInstance() {
		if (SystemStateLog.instance == null) {
			SystemStateLog.instance = new SystemStateLog();
		}
		return SystemStateLog.instance;
	}

	private SystemStateLog() {
		this.systemStates = new ArrayList<SystemRuntimeState>();
	}

	public synchronized void saveState(SystemRuntimeState state) {
		systemStates.add(state);
	}

	public List<SystemRuntimeState> getSystemStates() {
		return systemStates;
	}

	

}
