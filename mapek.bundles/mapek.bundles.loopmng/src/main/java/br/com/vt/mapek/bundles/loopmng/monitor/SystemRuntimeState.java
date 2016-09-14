package br.com.vt.mapek.bundles.loopmng.monitor;

import java.util.Date;

import br.com.vt.mapek.services.domain.ContextElement;

public class SystemRuntimeState {
	private Date collectionTime;
	private ContextElement context;

	public SystemRuntimeState(ContextElement context) {
		this.context = context;
		this.collectionTime = context.getCollectionTime();
	}
	
	public Date getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
	}

	public ContextElement getContext() {
		return context;
	}
	public void setContext(ContextElement context) {
		this.context = context;
	}



}
