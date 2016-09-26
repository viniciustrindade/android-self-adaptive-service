package br.com.vt.mapek.bundles.loop.main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import model.LoopXml;
import model.LoopXml.XLoop;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.extender.DeclarationBuilderService;
import org.apache.felix.ipojo.extender.DeclarationHandle;
import org.apache.felix.ipojo.extender.InstanceBuilder;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import br.com.vt.mapek.bundles.loop.ISerializerService;
import br.com.vt.mapek.services.ILoggerService;
import br.com.vt.mapek.services.IResource;
import br.com.vt.mapek.services.common.Util;

@Component
@Instantiate
public class LoopBuilder {

	List<DeclarationHandle> handlers = new ArrayList<DeclarationHandle>();
	private Bundle bundle;
	private BundleContext context;

	public LoopBuilder() {
		this.bundle = FrameworkUtil.getBundle(this.getClass());
		this.context = bundle.getBundleContext();

	}

	@Requires
	private DeclarationBuilderService service;

	@Requires
	private ILoggerService log;

	@Requires
	private ISerializerService serializer;

	@Requires
	private IResource resources;


	@Validate
	public void build() {
		
	
		log.D("[TRANSFORMS XML TO LOOPXML]");
		InputStream input = resources.getXML();
		LoopXml xml;
		try {
			xml = serializer.unmarshal(input, LoopXml.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		InstanceBuilder loopBuilder = service.newInstance("loop-factory");
	
		log.D("[CREATE LOOPS INSTANCES]\n");
		
		for (XLoop loop : xml.loops) {
			int loopID = Util.getNewID();
			String lid = "loop[" + loopID + "]";

			handlers.add(loopBuilder.name(lid).configure()
					.property("id", loopID).property("rate", loop.rate)
					.property("sensors", loop.sensors)
					.property("policys", loop.policys)
					.property("actions", loop.actions).build());
			log.D("[CREATED "+ lid + "]\n");
		
		}
		
		log.D("[PUBLISHING LOOPS]\n");
		for (DeclarationHandle handler : handlers) {
			handler.publish();
		}

	}

	@Invalidate
	public void destroy() {
		//UNPUBLISH ALL
		for (DeclarationHandle handler : handlers) {
			handler.retract();
		}
	}
	
	public <S> S registerService(Dictionary<String, String> properties,
			Class<S> clazz, S service) {
		context.registerService(clazz, service, properties);
		return service;
	}

}
