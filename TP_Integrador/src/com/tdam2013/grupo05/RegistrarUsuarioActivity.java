package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
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
		final String username = UtilesMensajesWeb.getUsername(this);
		if (username != null)
			editText.setText(username);

		getRegistrarUsuarioButton().setOnClickListener(
				new View.OnClickListener() {

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
									R.string.nombre_de_usuario_invalido,
									Toast.LENGTH_SHORT).show();
						}

					}
				});
	}

	private Button getRegistrarUsuarioButton() {
		return ((Button) findViewById(R.id.registrar_usuario_button));
	}

	private class RegisterUserTask extends AsyncTask<Object, Void, Void> {

		private final Context ctx;

		public RegisterUserTask(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(ctx);
			pd.setTitle(R.string.registrando_usuario);
			pd.setMessage(getString(R.string.registrando_usuario));
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

		@Override
		protected Void doInBackground(Object... params) {

			final Context ctx = (Context) params[0];
			final String username = (String) params[1];

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

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Toast.makeText(RegistrarUsuarioActivity.this,
								R.string.usuario_registrado_ok,
								Toast.LENGTH_SHORT).show();

						Intent data = new Intent();
						data.putExtra("username", username);
						setResult(RESULT_OK, data);
						finish();

					}

				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Toast.makeText(RegistrarUsuarioActivity.this,
								R.string.usuario_registrado_error,
								Toast.LENGTH_SHORT).show();

					}

				});

			}
			return null;
		}

	}

	public static Intent getRegistrarUsuarioActivityIntent(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				RegistrarUsuarioActivity.class.getCanonicalName()));
		return intent;
	}

}
