package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tdam2013.grupo05.utiles.UtilesIntents;

public class EnviarMensajeWebActivity extends Activity {

	/**
	 * Key to use in Intent.extra to declare destination of the message
	 */
	public static final String MSG_TO = "MSG_TO";

	/**
	 * Dialog ids
	 */
	public static final int DIALOG_ERROR = 1;

	public static final int ACTIVITY_REQUEST_CODE__ENTER_USERNAME = 1;

	/**
	 * Checks if the username exists in preferences. If doesn't exists, a new
	 * activity is launched to let the user enter his/her username.
	 */
	protected void checkUsername() {

		/*
		 * Check prefs
		 */
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);

		String nombreUsuarioWeb = sp.getString("pref_ldc_nombre_usuario_web",
				"").trim();

		Log.d("EnviarMensajeWebActivity", "pref_ldc_nombre_usuario_web: '"
				+ nombreUsuarioWeb + "'");

		if (nombreUsuarioWeb.length() == 0)
			startActivityForResult(
					UtilesIntents.getRegistrarUsuarioActivityIntent(this),
					ACTIVITY_REQUEST_CODE__ENTER_USERNAME);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enviar_mensaje_web_activity);

		((TextView) findViewById(R.id.enviar_mensaje_web_destinatario))
				.setText(this.getIntent().getExtras().getString(MSG_TO));

		/*
		 * Listeners
		 */
		getButton(R.id.enviar_mensaje_web_button).setOnClickListener(
				new EnviarMensajeWebOnClickListener());

	}

	@Override
	protected void onResume() {
		super.onResume();
		checkUsername();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_REQUEST_CODE__ENTER_USERNAME) {
			Log.i("", "requestCode: " + requestCode + ", resultCode: "
					+ resultCode + ", data: " + data);

			if (resultCode == RESULT_OK) {
				SharedPreferences sp = PreferenceManager
						.getDefaultSharedPreferences(this);
				Editor editor = sp.edit();
				editor.putString("pref_ldc_nombre_usuario_web", data
						.getExtras().getString("username").trim());
				editor.commit();

			} else {
				Log.w("onActivityResult()", "resultCode invalido: "
						+ resultCode);
			}

		} else {
			throw new RuntimeException("Invalid requestCode: " + requestCode);
		}
	}

	/**
	 * Dialog
	 */
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_ERROR) {
			Dialog dialog = new AlertDialog.Builder(this).setTitle("ERROR")
					.setMessage("No se ha podido enviar el mensaje web.")
					.create();
			return dialog;
		} else {
			return super.onCreateDialog(id);
		}
	}

	/**
	 * OnClickListener
	 */
	public class EnviarMensajeWebOnClickListener implements
			View.OnClickListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {

		}
	}

	/*
	 * Utiles
	 */
	public Button getButton(int id) {
		return (Button) this.findViewById(R.id.enviar_mensaje_web_button);
	}

}
