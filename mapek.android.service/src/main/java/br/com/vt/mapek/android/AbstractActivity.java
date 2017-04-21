package br.com.vt.mapek.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import br.com.vt.mapek.android.service.MapekOSGIService;
import br.com.vt.mapek.android.service.MapekOSGIService.MapekBinder;

public abstract class AbstractActivity extends Activity implements
		ServiceConnection {
	protected MapekOSGIService mService;
	boolean mBound = false;
	public static final String TAG = "MapekActivity";
	
	public void onServiceConnected(ComponentName className, IBinder service) {
		MapekBinder binder = (MapekBinder) service;
		mService = binder.getService(AbstractActivity.this);
		mService.notificar();
		mService.initServiceTracker();
		mBound = true;
	}

	public void onServiceDisconnected(ComponentName arg0) {
		mBound = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		startService();

	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStart");
		super.onStop();
		stopService();
	}
	
	protected void startService(){
		// bind the service
		Intent service = new Intent(this, MapekOSGIService.class);
		bindService(service, this, Context.BIND_AUTO_CREATE);
	}
	protected void stopService(){
		// Unbind from the service
		if (mBound) {
			unbindService(this);
			mBound = false;
		}
	}

}
