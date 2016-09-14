package br.com.vt.mapek.loopmng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.osgi.framework.ServiceReference;

import br.com.vt.mapek.services.IFileService;

class FileServiceTest implements IFileService {
	private ClassLoader classLoader;
	private static IFileService fileServiceTest;

	public static IFileService newInstance() {
		if (FileServiceTest.fileServiceTest == null) {
			FileServiceTest.fileServiceTest = new FileServiceTest();
		}
		return FileServiceTest.fileServiceTest;
	}

	public FileServiceTest() {
		Thread.currentThread().setContextClassLoader(
				IFileService.class.getClassLoader());
		this.classLoader = Thread.currentThread().getContextClassLoader();
	}

	
	public InputStream getInputStream(String filename) {
		try {
			return new FileInputStream(new File(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public OutputStream getOutputStream(String filename) {
		try {
			return new FileOutputStream(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void registered(ServiceReference ref) {
		// TODO Auto-generated method stub

	}

	public void unregistered(ServiceReference ref) {
		// TODO Auto-generated method stub

	}

}