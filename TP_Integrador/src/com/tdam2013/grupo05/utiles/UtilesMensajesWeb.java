package com.tdam2013.grupo05.utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UtilesMensajesWeb {

	public static final String PREF_USERNAME = "pref_ldc_nombre_usuario_web";

	public static boolean usernameIsValid(String username) {
		// FIXME: mejorare esta logica
		if (username != null && username.trim().length() > 0)
			return true;
		return false;
	}

	/**
	 * Returns registered username in web server (read from preferences)
	 * 
	 * @return
	 */
	public static String getUsername(Context ctx) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		return sp.getString(PREF_USERNAME, null);
	}

	/**
	 * Save on preferences the username
	 * 
	 * @return
	 */
	public static void setUsername(Context ctx, String username) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Editor editor = sp.edit();
		editor.putString(PREF_USERNAME, username);
		editor.commit();
	}

}
