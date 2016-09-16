package br.com.vt.mapek.bundles.loopmng.services;

import java.io.InputStream;
import java.io.OutputStream;

import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops;

public interface ISerializerService{

	public void marshal(OutputStream output, XMLLoops object) throws Exception;

	public XMLLoops unmarshal(InputStream input, Class<XMLLoops> clazz)
			throws Exception;

}