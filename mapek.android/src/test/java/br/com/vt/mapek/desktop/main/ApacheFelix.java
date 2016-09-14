package br.com.vt.mapek.desktop.main;

import org.junit.Test;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ServiceTestCase;
import android.test.mock.MockContext;
import br.vt.com.mapek.android.service.MapekOSGIService;

public class ApacheFelix extends ServiceTestCase<MapekOSGIService> {

	public ApacheFelix() {
		super(MapekOSGIService.class);
	}

	Service service;

	protected void setUp() throws Exception {
		super.setUp();
		Context c = new DelegatedMockContext(getContext());
		setContext(c);
		service = getService();
		
	}
	
	@Test
	public void testXXX(){
		assertNull(service);
	}
	class DelegatedMockContext extends MockContext {
		private Context mDelegatedContext;
		private static final String PREFIX = "test.";

		public DelegatedMockContext(Context context) {
			mDelegatedContext = context;
		}

		@Override
		public String getPackageName() {
			return PREFIX;
		}

		@Override
		public SharedPreferences getSharedPreferences(String name, int mode) {
			return mDelegatedContext.getSharedPreferences(name, mode);
		}
	}
}
