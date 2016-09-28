package br.com.vt.mapek.bundles.loop;

import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializerService {

	public <T> void marshal(OutputStream output, T object) throws Exception;

	public <T> T unmarshal(InputStream input, Class<T> clazz) throws Exception;

}