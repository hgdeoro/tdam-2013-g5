package com.tdam2013.grupo05;

import com.tdam2013.grupo05.RegistrarUsuarioActivity.RegisterUserTask;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IngresarUsuarioDeContactoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingresar_nombre_usuario_de_contacto_activity);

		getButton().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String username = getEditText().getText().toString().trim();
				if (UtilesMensajesWeb.usernameIsValid(username)) {

					Toast.makeText(IngresarUsuarioDeContactoActivity.this,
							"NOMBRE VALIDO! :_D", Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(IngresarUsuarioDeContactoActivity.this,
							R.string.nombre_de_usuario_invalido,
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private Button getButton() {
		return ((Button) findViewById(R.id.ingresar_nombre_usuario_contacto_button));
	}

	private EditText getEditText() {
		return ((EditText) findViewById(R.id.ingresar_nombre_usuario_contacto_edit_text));
	}

}
