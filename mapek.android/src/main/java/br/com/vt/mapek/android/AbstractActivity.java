package br.com.vt.mapek.android;

import br.vt.com.mapek.android.service.MapekOSGIService;
import br.vt.com.mapek.android.service.MapekOSGIService.MapekBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public abstract class AbstractActivity extends Activity {
	private MapekOSGIService mService;
	boolean mBound = false;
	static final String TAG = "MapekActivity";

	private ServiceConnection conn = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			MapekBinder binder = (MapekBinder) service;
			mService = binder.getService(AbstractActivity.this);
			mService.notificar();
			mService.initServiceTracker();
			mService.printBundleState();
			mBound = true;
		}

		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};
	
	

	@Override
	protected void onStart() {
		super.onStart();

		Intent service = new Intent(this, MapekOSGIService.class);
		bindService(service, conn, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStart");
		super.onStop();
		// Unbind from the service
		if (mBound) {
			unbindService(conn);
			mBound = false;
		}

	}
}
