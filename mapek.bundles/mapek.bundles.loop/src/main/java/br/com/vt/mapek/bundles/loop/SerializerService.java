package br.com.vt.mapek.bundles.loop;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.com.vt.mapek.services.ILoggerService;

/**
 * https://netbeans.org/bugzilla/show_bug.cgi?id=173878
 * http://hg.netbeans.org/web-main/rev/ab59a6dd6389
 * http://www.one-inside.com/consuming-webservices-using-apache-cxf-in-cq5-6-1/
 * set classloader to CXF bundle class loader to avoid OSGI classloader problems
 **/
@Component(publicFactory=false)
@Provides
@Instantiate
public class SerializerService implements ISerializerService  {

	@Requires
	private ILoggerService log;

	public <T> void marshal(OutputStream output, T object) throws Exception {
		
		ClassLoader orig = Thread.currentThread().getContextClassLoader();    
		Thread.currentThread().setContextClassLoader(
				Persister.class.getClassLoader());
		try {

			Serializer serializer = new Persister();
			serializer.write(object, output);
			
		} finally {
		    Thread.currentThread().setContextClassLoader(orig);
		}
		

	}

	public <T> T unmarshal(InputStream input, Class<T> clazz)
			throws Exception {
		ClassLoader orig = Thread.currentThread().getContextClassLoader();    
		Thread.currentThread().setContextClassLoader(
				Persister.class.getClassLoader());
		try {

			Serializer serializer = new Persister();
			return serializer.read(clazz, input);
		} finally {
		    Thread.currentThread().setContextClassLoader(orig);
		}

	}

}
