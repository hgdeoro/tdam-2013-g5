package ar.com.hgdeoro.tdam.ejercicio01;


import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.config);
    }

}
