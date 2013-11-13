package com.tdam2013.grupo05.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.tdam2013.grupo05.R;
import com.tdam2013.grupo05.utiles.UtilesHttp;

public class ListaDeContactosSettingsActivity extends PreferenceActivity
		implements OnSharedPreferenceChangeListener {

	public static final UtilesHttp utilesHttp = new UtilesHttp();

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

		if ("pref_ldc_nombre_usuario_web".equals(key)) {
			AsyncTask<Object, Void, Void> task = new RegisterUserTask();
			task.execute(getApplicationContext(),
					sharedPreferences.getString(key, ""));
		}

	}

	/*
	 * Atencion! Esto es Copy&Paste de EnviarMensajeWebActivity.RegisterUserTask
	 */
	protected class RegisterUserTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {

			final Context ctx = (Context) params[0];
			final String username = (String) params[1];

			boolean ok;
			try {
				ok = utilesHttp.registerUser(username);
			} catch (Exception e) {
				Log.wtf("registerUser()", e);
				ok = false;
			}

			if (ok)
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								ctx,
								"El nombre de usuario fue creado en el servidor.",
								Toast.LENGTH_SHORT).show();
					}
				});
			else
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								ctx,
								"ERROR: el usuario no fue registrado en el servidor",
								Toast.LENGTH_SHORT).show();
					}
				});

			return null;
		}

	}
}
