package br.com.vt.mapek.services;

import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializerService<T> {

	public void marshal(OutputStream output, T object) throws Exception;

	public T unmarshal(InputStream input, Class<T> clazz)
			throws Exception;

}