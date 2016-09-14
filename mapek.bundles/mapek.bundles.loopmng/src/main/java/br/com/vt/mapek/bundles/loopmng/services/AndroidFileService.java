/*package br.com.vt.mapek.bundles.loopmng.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.framework.BundleContext;

import android.content.Context;
import android.content.res.Resources;
import br.com.vt.mapek.services.IFileService;
import br.com.vt.mapek.services.ILoggerService;

@Component
@Provides
@Instantiate
public class AndroidFileService implements IFileService {

	@Requires
	private ILoggerService log;
	

	@Requires
	public Context androidCTX;



	public InputStream getInputStream(String filename) {
		String file = filename.replaceAll("[.][a-zA-Z0-9]*", "");
		String pack = androidCTX.getPackageName();
		Resources res = androidCTX.getResources();

		log.D("[INPUT] filename : " + filename + ":raw:" + pack);

		int resId = res.getIdentifier(file, "raw", pack);
		InputStream input = res.openRawResource(resId);

		if (input == null) {
			try {
				throw new FileNotFoundException("Recurso nao encontrado!!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return input;

	}

	public OutputStream getOutputStream(String filename) {
		try {
			String dir = androidCTX.getExternalCacheDir() + File.separator
					+ filename;
			log.D("[OUTPUT] dir : " + dir);

			return new FileOutputStream(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	

}
*/