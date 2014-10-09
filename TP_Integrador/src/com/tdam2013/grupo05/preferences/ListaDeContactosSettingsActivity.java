package com.tdam2013.grupo05.preferences;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

	public static Intent getListaDeContactosSettingsActivity(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				ListaDeContactosSettingsActivity.class.getCanonicalName()));
		return intent;
	}

}
