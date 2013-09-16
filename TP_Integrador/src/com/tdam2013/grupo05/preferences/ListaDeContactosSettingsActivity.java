package com.tdam2013.grupo05.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tdam2013.grupo05.R;

public class ListaDeContactosSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.lista_de_contactos_settings_activity);
        this.addPreferencesFromResource(R.xml.preference_lista_de_contactos);
    }

}
