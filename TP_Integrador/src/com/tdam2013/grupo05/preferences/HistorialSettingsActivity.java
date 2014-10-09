package com.tdam2013.grupo05.preferences;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tdam2013.grupo05.R;

public class HistorialSettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.preference_historial);
	}

	public static Intent getHistorialSettingsActivity(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				HistorialSettingsActivity.class.getCanonicalName()));
		return intent;
	}

}
