package br.com.vt.mapek.services.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;


public class Util {
	private static int LASTID = 1;
	public static final DateFormat dtFormat = new SimpleDateFormat("hh:ss");

	public static int getNewID() {
		return Util.LASTID++;
	}

	public static int getRandomValues(int maxValue) {
		int aux = (new Random().nextInt() % maxValue);
		aux = aux < 0 ? aux * (-1) : aux;
		return aux;
	}




}
