package br.com.vt.mapek.services;

import java.io.InputStream;
import java.io.OutputStream;

public interface IFileService {

	public InputStream getInputStream(String filename);

	public OutputStream getOutputStream(String filename);

}