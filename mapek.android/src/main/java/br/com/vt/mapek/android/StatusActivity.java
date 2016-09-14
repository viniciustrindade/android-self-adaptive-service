package br.com.vt.mapek.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import br.com.vt.mapek.R;
import br.vt.com.mapek.android.service.MapekOSGIService;
import br.vt.com.mapek.android.service.MapekOSGIService.MapekBinder;

/**
 * http://stackoverflow.com/questions/2784441/trying-to-start-a-service-on-boot-
 * on-android
 * http://stackoverflow.com/questions/18924640/starting-android-service
 * -using-explicit-vs-implicit-intent
 * http://stackoverflow.com/questions/10909683
 * /launch-android-application-without
 * -main-activity-and-start-service-on-launching
 * http://stackoverflow.com/questions
 * /7690350/android-start-service-on-boot/7690600#7690600
 * 
 * @author vinicius
 *
 */
public class StatusActivity extends AbstractActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	}

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart");
		super.onStart();
	}

}
