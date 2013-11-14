package com.tdam2013.grupo05.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnectedOrConnecting = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return isConnectedOrConnecting;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.i("---------- onReceive ----------", "");
		Log.i("@isConnected()", "" + isConnected(context));
		Log.i("getAction()", "" + intent.getAction());
		Log.i("getDataString()", "" + intent.getDataString());
		Log.i("getFlags()", "" + intent.getFlags());
		Log.i("getType()", "" + intent.getType());
		Log.i("getComponent()", "" + intent.getComponent());
		Log.i("getData()", "" + intent.getData());
		Log.i("getExtras()", "" + intent.getExtras().toString());

		Toast.makeText(context, "isC(): " + isConnected(context),
				Toast.LENGTH_SHORT).show();

	}

}
