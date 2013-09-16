package com.tdam2013.grupo05.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tdam2013.grupo05.R;

public class HistorialSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preference_historial);
    }

}
