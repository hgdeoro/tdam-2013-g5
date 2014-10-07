package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.utiles.UtilesIntents;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;

public class IngresarUsuarioDeContactoActivity extends Activity {

	private Long contactId = null;
	private String displayName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingresar_nombre_usuario_de_contacto_activity);

		// Reusamos constantes de AccionesSobreContactoActivity
		contactId = this.getIntent().getExtras()
				.getLong(AccionesSobreContactoActivity.CONTACT_ID);
		displayName = this.getIntent().getExtras()
				.getString(AccionesSobreContactoActivity.DISPLAY_NAME);

		getButton().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final String username = getEditText().getText().toString()
						.trim();
				if (UtilesMensajesWeb.usernameIsValid(username)) {

					Log.i("IngresarUsuarioDeContactoActivity",
							"Guardando nombre de usuario:  " + username
									+ " para contacto: " + contactId);

					// FIXME: esto se hace en main-thread. Pero solo un
					// select + update/insert
					Database.getDatabase(IngresarUsuarioDeContactoActivity.this)
							.insertUsernameDeContacto(contactId, username);

					Intent intentNewActivity = UtilesIntents
							.getEnviarMensajeWebActivityIntent(
									IngresarUsuarioDeContactoActivity.this,
									username, contactId, displayName);

					// Necesitamos FLAG_ACTIVITY_CLEAR_TOP para que el back
					// stack quede bien seteado, y no quede en el back stack la
					// activity que muestra el detalle del mensaje recien
					// borrado!

					intentNewActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					IngresarUsuarioDeContactoActivity.this
							.startActivity(intentNewActivity);

				} else {
					mostrarErrorNombreDeUsuarioInvalido();
				}

			}
		});
	}

	private void mostrarErrorNombreDeUsuarioInvalido() {
		Toast.makeText(IngresarUsuarioDeContactoActivity.this,
				R.string.nombre_de_usuario_invalido, Toast.LENGTH_SHORT).show();

	}

	private Button getButton() {
		return ((Button) findViewById(R.id.ingresar_nombre_usuario_contacto_button));
	}

	private EditText getEditText() {
		return ((EditText) findViewById(R.id.ingresar_nombre_usuario_contacto_edit_text));
	}

}
