package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdam2013.grupo05.utiles.UtilesHttp;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;

public class RegistrarUsuarioActivity extends Activity {

	public static final UtilesHttp utilesHttp = new UtilesHttp();

	protected ProgressDialog pd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_usuario_activity);

		final EditText editText = (EditText) findViewById(R.id.registrar_usuario_text);
		if (UtilesMensajesWeb.getUsername(this) != null)
			editText.setText(UtilesMensajesWeb.getUsername(this));

		((Button) findViewById(R.id.registrar_usuario_button))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						String username = editText.getText().toString().trim();
						if (UtilesMensajesWeb.usernameIsValid(username)) {

							AsyncTask<Object, Void, Void> task = new RegisterUserTask(
									RegistrarUsuarioActivity.this);
							task.execute(RegistrarUsuarioActivity.this,
									username);

						} else {
							Toast.makeText(RegistrarUsuarioActivity.this,
									"El nombre de usuario no es valido.",
									Toast.LENGTH_SHORT).show();
						}

					}
				});
	}

	protected class RegisterUserTask extends AsyncTask<Object, Void, Void> {

		private final Context ctx;

		public RegisterUserTask(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(ctx);
			pd.setTitle("Registrando usuario...");
			pd.setMessage("Registrando usuario...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

		@Override
		protected Void doInBackground(Object... params) {

			final Context ctx = (Context) params[0];
			final String username = (String) params[1];

			// UtilesNotifications
			// .notifyWithIndeterminateProgress(ctx,
			// "Registrando usuario...", "Registrando usuario...",
			// "Registrando usuario...",
			// UtilesNotifications.REGISTER_USER);

			boolean ok;
			try {
				ok = utilesHttp.registerUser(username);
				if (ok)
					UtilesMensajesWeb.setUsername(ctx, username);

			} catch (Exception e) {
				Log.wtf("registerUser()", e);
				ok = false;
			}

			if (pd != null)
				pd.dismiss();

			if (ok) {
				// UtilesNotifications.notifyWithIndeterminateProgress(ctx,
				// "El usuario fue creado satisfactoriamente",
				// "El usuario fue creado satisfactoriamente",
				// "El usuario fue creado satisfactoriamente",
				// UtilesNotifications.REGISTER_USER);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Toast.makeText(
								RegistrarUsuarioActivity.this,
								"El usuario se ha registrado satisfactoriamente.",
								Toast.LENGTH_SHORT).show();

						Intent data = new Intent();
						data.putExtra("username", username);
						setResult(RESULT_OK, data);
						finish();

					}

				});

			} else {
				// UtilesNotifications.notifyWithIndeterminateProgress(ctx,
				// "ERROR: el usuario no fue registrado en el servidor",
				// "ERROR: el usuario no fue registrado en el servidor",
				// "ERROR: el usuario no fue registrado en el servidor",
				// UtilesNotifications.REGISTER_USER);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Toast.makeText(RegistrarUsuarioActivity.this,
								"El usuario no pudo registrarse.",
								Toast.LENGTH_SHORT).show();

					}

				});

			}
			return null;
		}

	}
}
