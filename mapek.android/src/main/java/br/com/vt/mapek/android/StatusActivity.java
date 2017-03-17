package br.com.vt.mapek.android;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import br.com.vt.mapek.R;
import br.com.vt.mapek.android.ui.BundleInfo;

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
public class StatusActivity extends AbstractActivity implements BundleListener {

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_list);
		
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart");
		super.onStart();

		
	}

	public void onServiceConnected(ComponentName className, IBinder service){
		super.onServiceConnected(className, service);
		mService.addBundleListener(this);
	}
	public void bundleChanged(BundleEvent event) {

		Bundle[] bundles = mService.getInstalledBundles();
		final TreeMap<Long,BundleInfo> bundleMap = new TreeMap<Long,BundleInfo>();

		String info = "Bundles Status: \n";
		int i = 0;
		for (Bundle b : bundles) {

			String status = "";
			int state = b.getState();
			boolean onoff = false;

			switch (state) {

			case Bundle.ACTIVE:
				status = "ACTIVE";
				onoff = true;
				break;
			case Bundle.INSTALLED:
				status = "INSTALLED";
				break;
			case Bundle.RESOLVED:
				status = "RESOLVED";
				break;
			case Bundle.STARTING:
				status = "STARTING";
				break;
			case Bundle.STOPPING:
				status = "STOPPING";
				break;
			case Bundle.UNINSTALLED:
				status = "UNINSTALLED";
				break;
			default:
				status = "UNKNOWN STATE";

			}
			if (status.length() == 0)
				status = "UNKNOWN STATE";

			info = info + "\n[" + status + "] " + b.getSymbolicName() + " ("
					+ b.getVersion() + ")";
			BundleInfo bundleInfo = new BundleInfo(b.getBundleId(), b.getSymbolicName(), status, onoff);
			bundleMap.put(b.getLastModified(),bundleInfo);
		}
		final List<BundleInfo> list = new ArrayList<BundleInfo>();
		for (Long key :bundleMap.descendingKeySet()){
			list.add(bundleMap.get(key));
		}
		
		System.out.println(info);
		this.runOnUiThread(new Runnable() {

			public void run() {
				ListView listView = (ListView) findViewById(R.id.list);
				
				BundleInfoAdapter adapter = new BundleInfoAdapter(
						StatusActivity.this, R.id.list,list);
				listView.setAdapter(adapter);
			}
		});

	}

	class BundleInfoAdapter extends ArrayAdapter<BundleInfo> {

		private ArrayList<BundleInfo> bundleList;

		public BundleInfoAdapter(Context context, int textViewResourceId,
				List<BundleInfo> bundleList) {
			super(context, textViewResourceId, bundleList);
			this.bundleList = new ArrayList<BundleInfo>();
			this.bundleList.addAll(bundleList);
		}

		private class ViewHolder {
			TextView nome;
			Switch onoff;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.bundle_info, null);

				holder = new ViewHolder();
				holder.nome = (TextView) convertView.findViewById(R.id.nome);
				holder.onoff = (Switch) convertView
						.findViewById(R.id.onoff);
				convertView.setTag(holder);

				holder.onoff.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Switch tb = (Switch) v;
						BundleInfo bundleInfo = (BundleInfo) tb.getTag();
						if (tb.isChecked()){
							mService.stopBundle(bundleInfo.getId());
						}else{
							mService.startBundle(bundleInfo.getId());
						}
			/*			Toast.makeText(
								getApplicationContext(),
								"Bundle " + tb.getText() + " is "
										+ bundleInfo.getStatus(), Toast.LENGTH_LONG)
								.show();*/
						//bundleInfo.setOnoff(tb.isChecked());
					}
				});
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			BundleInfo info = bundleList.get(position);
			holder.nome.setText(info.getNome());
			holder.onoff.setChecked(info.isOnoff());
			holder.onoff.setText(info.getStatus());
			holder.onoff.setTag(info);

			return convertView;

		}
	}

}
