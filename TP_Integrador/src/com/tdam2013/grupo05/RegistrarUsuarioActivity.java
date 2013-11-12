package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrarUsuarioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_usuario_activity);

		final EditText editText = (EditText) findViewById(R.id.registrar_usuario_text);

		((Button) findViewById(R.id.registrar_usuario_button))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						String username = editText.getText().toString().trim();
						Intent data = new Intent();
						data.putExtra("username", username);
						setResult(RESULT_OK, data);
						finish();

					}
				});
	}

}
