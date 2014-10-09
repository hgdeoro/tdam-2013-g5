package com.tdam2013.grupo05.utiles;

import android.content.Intent;
import android.net.Uri;

/**
 * Clase utilitaria. Los metodos generan intents para lanzar activities.
 * 
 * @author Horacio G. de Oro
 *
 */
public class UtilesIntents {

	/*
	 * Android
	 */
	public static Intent getCallPhoneIntent(String numero) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		// intent.setData(Uri.parse("tel:" + numero));
		intent.setData(Uri.fromParts("tel", numero, null));
		return intent;
	}

	/*
	 * Android
	 */
	public static Intent getSendSmsIntent(String numero) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.setData(Uri.parse("sms:" + numero));
		intent.setData(Uri.fromParts("sms", numero, null));
		return intent;
	}

	/*
	 * Android
	 */
	public static Intent getSendEmailIntent(String to) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.fromParts("mailto", to, null));
		return intent;
	}

}
