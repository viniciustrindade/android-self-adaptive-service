package br.com.vt.mapek.android.services.context;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryLevelReceiver  extends BroadcastReceiver {

    private static final String TAG = "BatteryLevelReceiver";
	
	    @Override
	    public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive");
	    	IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	    	Intent batteryStatus = context.registerReceiver(null, ifilter);
	    	
	        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	    	int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	    	int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

	    	float batteryPct = level / (float)scale;
	    	
			Log.i("CAFD","Bateria "+ batteryPct);
	    	
	    	
	    }
	}
