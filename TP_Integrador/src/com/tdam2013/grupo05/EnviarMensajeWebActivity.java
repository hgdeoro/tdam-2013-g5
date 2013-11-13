package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam2013.grupo05.utiles.UtilesHttp;
import com.tdam2013.grupo05.utiles.UtilesIntents;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;

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

	public static final UtilesHttp utilesHttp = new UtilesHttp();

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

		if (!UtilesMensajesWeb.usernameIsValid(nombreUsuarioWeb))
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

		/*
		 * este chequeo lo hacemos en onResume() para que funciona al cargar la
		 * activity, y tambien luego de ejecutar onActivityResult().
		 */
		checkUsername();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_REQUEST_CODE__ENTER_USERNAME) {
			Log.i("", "requestCode: " + requestCode + ", resultCode: "
					+ resultCode + ", data: " + data);

			if (resultCode == RESULT_OK) {

				final String username = data.getExtras().getString("username")
						.trim();

				if (UtilesMensajesWeb.usernameIsValid(username)) {
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(this);
					Editor editor = sp.edit();
					editor.putString("pref_ldc_nombre_usuario_web", username);
					editor.commit();

					Toast.makeText(this,
							"El nombre de usuario fue guardado correctamente.",
							Toast.LENGTH_SHORT).show();

					// FIXME: quitar esto de thread de UI
					boolean ok;
					try {
						ok = utilesHttp.registerUser(username);
					} catch (Exception e) {
						Log.wtf("registerUser()", e);
						ok = false;
					}

					if (ok)
						Toast.makeText(
								this,
								"El nombre de usuario fue creado en el servidor.",
								Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(
								this,
								"ERROR: el usuario no fue registrado en el servidor",
								Toast.LENGTH_SHORT).show();

					return;
				}

				Toast.makeText(this, "No se ha seteado el nombre de usuario.",
						Toast.LENGTH_SHORT).show();

			} else {
				Log.w("onActivityResult()", "resultCode invalido: "
						+ resultCode);
				finish();
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

		@Override
		public void onClick(View v) {

			final String destinatario = ((TextView) findViewById(R.id.enviar_mensaje_web_destinatario))
					.getText().toString();
			final String mensaje = ((EditText) findViewById(R.id.enviar_mensaje_web_text))
					.getText().toString();

			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(EnviarMensajeWebActivity.this);
			final String from = sp.getString("pref_ldc_nombre_usuario_web", "")
					.trim();

			AsyncTask<Object, Void, Void> task = new SendMessageTask();
			task.execute(EnviarMensajeWebActivity.this.getApplicationContext(),
					from, destinatario, mensaje);

			finish();

		}
	}

	protected class SendMessageTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {

			final Context ctx = (Context) params[0];
			final String from = (String) params[1];
			final String destinatario = (String) params[2];
			final String mensaje = (String) params[3];

			// FIXME: quitar esto de thread de UI
			boolean ok;
			try {
				ok = utilesHttp.sendMessage(from, destinatario, mensaje);
			} catch (Exception e) {
				Log.wtf("sendMessage()", e);
				ok = false;
			}

			if (ok)
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(ctx,
								"El mensaje fue enviado correctamente.",
								Toast.LENGTH_SHORT).show();
					}
				});
			else
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(ctx,
								"ERROR: el mensaje no fue enviado.",
								Toast.LENGTH_SHORT).show();
					}
				});

			return null;
		}

	}

	/*
	 * Utiles
	 */
	public Button getButton(int id) {
		return (Button) this.findViewById(R.id.enviar_mensaje_web_button);
	}

}
