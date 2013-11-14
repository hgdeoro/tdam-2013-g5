package com.tdam2013.grupo05.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tdam2013.grupo05.R;

public class ListaDeContactosSettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.preference_lista_de_contactos);
	}

	// @Override
	// public void onSharedPreferenceChanged(SharedPreferences
	// sharedPreferences,
	// String key) {
	//
	// if ("pref_ldc_nombre_usuario_web".equals(key)) {
	// AsyncTask<Object, Void, Void> task = new RegisterUserTask();
	// task.execute(getApplicationContext(),
	// sharedPreferences.getString(key, ""));
	// }
	//
	// }

}
