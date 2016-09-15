package br.com.vt.mapek.bundles.loopmng.services;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.com.vt.mapek.bundles.loopmng.domain.XMLLoops;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.ISerializerService;

@Component
@Provides
@Instantiate
public class SerializerService implements ISerializerService<XMLLoops> {
	private Serializer serializer;
	
	@Requires
	private ILoggerService log;
	
	/** https://netbeans.org/bugzilla/show_bug.cgi?id=173878
	// http://hg.netbeans.org/web-main/rev/ab59a6dd6389
	// http://www.one-inside.com/consuming-webservices-using-apache-cxf-in-cq5-6-1/
	// set classloader to CXF bundle class loader to avoid OSGI classloader
	// problems  **/
	public SerializerService() {
		ClassLoader orig = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(
				Persister.class.getClassLoader());
		this.serializer = new Persister();
	}


	public void marshal(OutputStream output, XMLLoops object) throws Exception {
		serializer.write(object, output);
	}

	public XMLLoops unmarshal(InputStream input, Class<XMLLoops> clazz)
			throws Exception {
		return serializer.read(clazz, input);
	}
	

}
