package br.com.vt.mapek.android.felix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.felix.framework.Logger;
import org.apache.felix.framework.cache.BundleCache;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.StringMap;
import org.osgi.framework.Constants;

import android.os.Environment;
import android.util.Log;

public class FelixConfig extends StringMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1622230901164262179L;
	private static final String TAG = "[MAPEKFELIX]";
	private static final String FELIX_BUNDLES_DIR = "/data/felix/bundles";
	private static final String FRAMEWORK_CAPABILITIES = "osgi.ee; osgi.ee=\"JavaSE\";version:List=\"1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8\"";
	private static final String ANDROID_FRAMEWORK_PACKAGES = (
			/* org.apache.felix.frameword-5.4.0 */
			"org.osgi.framework;version=\"1.8\",org.osgi.framework.dto;"
					+ "version=\"1.8\";uses:=\"org.osgi.dto\",org.osgi.framework.hooks.bundle;vers"
					+ "ion=\"1.1\";uses:=\"org.osgi.framework\",org.osgi.framework.hooks.resolver;"
					+ "version=\"1.0\";uses:=\"org.osgi.framework.wiring\",org.osgi.framework.hook"
					+ "s.service;version=\"1.1\";uses:=\"org.osgi.framework\",org.osgi.framework.h"
					+ "ooks.weaving;version=\"1.1\";uses:=\"org.osgi.framework.wiring\",org.osgi.f"
					+ "ramework.launch;version=\"1.2\";uses:=\"org.osgi.framework\",org.osgi.frame"
					+ "work.namespace;version=\"1.1\";uses:=\"org.osgi.resource\",org.osgi.framewo"
					+ "rk.startlevel;version=\"1.0\";uses:=\"org.osgi.framework\",org.osgi.framewo"
					+ "rk.startlevel.dto;version=\"1.0\";uses:=\"org.osgi.dto\",org.osgi.framework"
					+ ".wiring;version=\"1.2\";uses:=\"org.osgi.framework,org.osgi.resource\",org."
					+ "osgi.framework.wiring.dto;version=\"1.2\";uses:=\"org.osgi.dto,org.osgi.re"
					+ "source.dto\",org.osgi.resource;version=\"1.0\",org.osgi.resource.dto;versi"
					+ "on=\"1.0\";uses:=\"org.osgi.dto\",org.osgi.service.packageadmin;version=\"1."
					+ "2\";uses:=\"org.osgi.framework\",org.osgi.service.startlevel;version=\"1.1\""
					+ ";uses:=\"org.osgi.framework\",org.osgi.service.url;version=\"1.0\",org.osgi"
					+ ".service.resolver;version=\"1.0\";uses:=\"org.osgi.resource\",org.osgi.util"
					+ ".tracker;version=\"1.5.1\";uses:=\"org.osgi.framework\",org.osgi.dto;versio"
					+ "n=\"1.0\","
					/* org.apache.felix.fileinstall-3.5.4 */
					+ "org.apache.felix.fileinstall;version=\"3.5.4\"," + "android; "
					+ "android.app;" + "android.content;" + "android.content.res;"
					+ "android.database;" + "android.database.sqlite;"
					+ "android.graphics; " + "android.graphics.drawable; "
					+ "android.graphics.glutils; " + "android.hardware; "
					+ "android.location; " + "android.media; " + "android.net; "
					+ "android.opengl; " + "android.os; " + "android.provider; "
					+ "android.sax; " + "android.speech.recognition; "
					+ "android.telephony; " + "android.telephony.gsm; "
					+ "android.text; " + "android.text.method; "
					+ "android.text.style; " + "android.text.util; " + "android.util; "
					+ "android.view; " + "android.view.animation; "
					+ "android.webkit; " + "android.widget; "
					+ "com.google.android.maps; " + "com.google.android.xmppService; "
					+ "javax.crypto; " + "javax.crypto.interfaces; "
					+ "javax.crypto.spec; " + "javax.microedition.khronos.opengles; "
					+ "javax.net; " + "javax.net.ssl; " + "javax.security.auth; "
					+ "javax.security.auth.callback; " + "javax.security.auth.login; "
					+ "javax.security.auth.x500; " + "javax.security.cert; "
					+ "javax.sound.midi; " + "javax.sound.midi.spi; "
					+ "javax.sound.sampled; " + "javax.sound.sampled.spi; "
					+ "javax.sql; " + "javax.xml.parsers; " + "junit.extensions; "
					+ "junit.framework; " + "org.apache.commons.codec; "
					+ "org.apache.commons.codec.binary; "
					+ "org.apache.commons.codec.language; "
					+ "org.apache.commons.codec.net; "
					+ "org.apache.commons.httpclient; "
					+ "org.apache.commons.httpclient.auth; "
					+ "org.apache.commons.httpclient.cookie; "
					+ "org.apache.commons.httpclient.methods; "
					+ "org.apache.commons.httpclient.methods.multipart; "
					+ "org.apache.commons.httpclient.params; "
					+ "org.apache.commons.httpclient.protocol; "
					+ "org.apache.commons.httpclient.util; " + "org.bluez; "
					+ "org.json; " + "org.w3c.dom; " + "org.xml.sax; "
					+ "org.xml.sax.ext; " + "org.xml.sax.helpers; "
					+ "version=1.0.0.m5-r15," + "br.com.vt.mapek.android;"
				/*	+ "org.simpleframework.xml,"
					+ "org.simpleframework.xml.convert,"
					+ "org.simpleframework.xml.core,"
					+ "org.simpleframework.xml.filter,"
					+ "org.simpleframework.xml.strategy,"
					+ "org.simpleframework.xml.stream,"
					+ "org.simpleframework.xml.transform,"
					+ "org.simpleframework.xml.util,"*/
					+ "br.com.vt.mapek.services;" + "br.com.vt.mapek.services.domain;"
					+ "br.com.vt.mapek.services.common;" + "br.com.vt.mapek.services.context;"
					+ "br.com.vt.mapek.android.services;"
					+ "br.com.vt.mapek.android.services.context;"
					+ "br.com.vt.mapek.android.context;" + "br.com.vt.mapek.felix.view").intern();
	
	
	
	private File bundleDir;
	private File cacheDir;

	public FelixConfig() {

		put("ipojo.proxy", "disabled");
		put("ipojo.log.level", "debug");
		put(FelixConstants.FRAMEWORK_STORAGE_CLEAN,
				FelixConstants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
		put(FelixConstants.LOG_LEVEL_PROP, String.valueOf(Logger.LOG_DEBUG));
		// put(DirectoryWatcher.LOG_LEVEL, Logger.LOG_DEBUG);

		put(Constants.FRAMEWORK_SYSTEMCAPABILITIES, FRAMEWORK_CAPABILITIES);
		put(Constants.FRAMEWORK_SYSTEMPACKAGES, ANDROID_FRAMEWORK_PACKAGES);
		configureBundlesDir();
		configureCache();

	}

	public void configureBundlesDir() {

		bundleDir = new File(Environment.getExternalStorageDirectory()
				.getPath() + FELIX_BUNDLES_DIR);
		if (!bundleDir.exists()) {
			if (!bundleDir.mkdirs()) {
				throw new IllegalStateException("Unable to create bundles dir");
			}
		}

		// put(DirectoryWatcher.DIR, bundleDir.getAbsolutePath());
	}

	public void configureCache() {
		/***************** CACHE **********************/
		// Explicitly specify the directory to use for caching bundles.
		try {
			cacheDir = File.createTempFile("felix-cache", null);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		cacheDir.delete();
		cacheDir.mkdirs();

		put(BundleCache.CACHE_ROOTDIR_PROP, ".");
		put(FelixConstants.FRAMEWORK_STORAGE, cacheDir.getAbsolutePath());
	}

	public void configureLogger() {
		PrintStream out = new PrintStream(new OutputStream() {
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			@Override
			public void write(int oneByte) throws IOException {
				output.write(oneByte);
				if (oneByte == '\n') {
					Log.v(TAG, new String(output.toByteArray()));
					output = new ByteArrayOutputStream();
				}
			}
		});
		System.setErr(out);
		System.setOut(out);
	}

	public File getCacheDir() {
		return cacheDir;
	}

	public File getBundleDir() {
		return bundleDir;
	}

}
