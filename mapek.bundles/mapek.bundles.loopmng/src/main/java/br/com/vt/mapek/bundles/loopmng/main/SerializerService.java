package br.com.vt.mapek.bundles.loopmng.main;

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

@Component
@Provides
@Instantiate
public class SerializerService implements ISerializerService {
	private static ISerializerService serializerManager;
	private Serializer serializer;
	
	@Requires
	private ILoggerService log;

	public SerializerService() {
		// https://netbeans.org/bugzilla/show_bug.cgi?id=173878
		// http://hg.netbeans.org/web-main/rev/ab59a6dd6389
		// http://www.one-inside.com/consuming-webservices-using-apache-cxf-in-cq5-6-1/
		// set classloader to CXF bundle class loader to avoid OSGI classloader
		// problems 

		ClassLoader orig = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(
				Persister.class.getClassLoader());
		this.serializer = new Persister();
	}

	public static ISerializerService newInstance() {
		if (SerializerService.serializerManager == null) {
			SerializerService.serializerManager = new SerializerService();
		}
		return SerializerService.serializerManager;
	}

	public void marshal(OutputStream output, XMLLoops object) throws Exception {
		serializer.write(object, output);
	}

	public XMLLoops unmarshal(InputStream input, Class<XMLLoops> clazz)
			throws Exception {
		return serializer.read(clazz, input);
	}
	

}
