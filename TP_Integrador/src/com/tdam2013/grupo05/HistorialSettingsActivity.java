package com.tdam2013.grupo05;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class HistorialSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preference_historial);
    }

}
