package com.tdam2013.grupo05.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.tdam2013.grupo05.R;

public class ListaDeContactosSettingsActivity extends PreferenceActivity
		implements OnSharedPreferenceChangeListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.lista_de_contactos_settings_activity);
		this.addPreferencesFromResource(R.xml.preference_lista_de_contactos);

		PreferenceManager.getDefaultSharedPreferences(this)
				.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.i("onSharedPreferenceChanged", "key: " + key);
	}

}
