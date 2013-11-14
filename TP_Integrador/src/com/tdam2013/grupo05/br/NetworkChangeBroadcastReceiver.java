package com.tdam2013.grupo05.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

	public boolean isConnectedOrConnecting(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnectedOrConnecting = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return isConnectedOrConnecting;
	}

	public boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnected();
		return isConnected;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.i("---------- onReceive ----------", "");
		Log.i("@isConnectedOrConnecting()", ""
				+ isConnectedOrConnecting(context));
		Log.i("@isConnected()", "" + isConnected(context));
		Log.i("getAction()", "" + intent.getAction());
		Log.i("getDataString()", "" + intent.getDataString());
		Log.i("getFlags()", "" + intent.getFlags());
		Log.i("getType()", "" + intent.getType());
		Log.i("getComponent()", "" + intent.getComponent());
		Log.i("getData()", "" + intent.getData());
		Log.i("getExtras()", "" + intent.getExtras().toString());

	}

}
