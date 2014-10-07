package com.tdam2013.grupo05.utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UtilesMensajesWeb {

	public static final String PREF_USERNAME = "pref_ldc_nombre_usuario_web";

	public static boolean usernameIsValid(String username) {

		if (username == null) {
			new NullPointerException("username es null").printStackTrace();
			return false;
		}

		username = username.trim();

		// Se hizo "ingenieria reversa", y se encontro que lo unico que se
		// controla es el largo del username. Aceptó:
		//
		// '12345'
		// '123456789012'
		// 'CON ESPACIO'
		// 'CON@RROBA'
		//
		return username.length() >= 5 && username.length() <= 12;
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
