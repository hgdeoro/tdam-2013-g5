package com.tdam2013.grupo05.utiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UtilesNetwork {

	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnectedOrConnecting = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return isConnectedOrConnecting;
	}

}
