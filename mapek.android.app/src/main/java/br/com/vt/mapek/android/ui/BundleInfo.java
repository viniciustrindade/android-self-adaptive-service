package br.com.vt.mapek.android.ui;

public class BundleInfo {
	String nome = null;
	String status = null;
	boolean onoff = false;
	long id;

	public BundleInfo(long id, String nome, String status, boolean onoff) {
		super();
		this.nome = nome;
		this.onoff = onoff;
		this.status = status;
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isOnoff() {
		return onoff;
	}

	public void setOnoff(boolean onoff) {
		this.onoff = onoff;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
