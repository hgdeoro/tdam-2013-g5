package com.tdam2013.grupo05.preferences;

import com.tdam2013.grupo05.R;
import com.tdam2013.grupo05.R.xml;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class HistorialSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preference_historial);
    }

}
