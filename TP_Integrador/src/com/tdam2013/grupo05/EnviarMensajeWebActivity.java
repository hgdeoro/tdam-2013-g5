package com.tdam2013.grupo05;

import com.tdam2013.grupo05.utiles.UtilesIntents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EnviarMensajeWebActivity extends Activity {

	/**
	 * Key to use in Intent.extra to declare destination of the message
	 */
	public static final String MSG_TO = "MSG_TO";

	/**
	 * Dialog ids
	 */
	public static final int DIALOG_ERROR = 1;

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

		boolean mostrarDialogo = true;

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			if (mostrarDialogo) {
				EnviarMensajeWebActivity.this.showDialog(DIALOG_ERROR);
			} else {
				EnviarMensajeWebActivity.this
						.startActivity(UtilesIntents
								.getRegistrarUsuarioActivityIntent(EnviarMensajeWebActivity.this));
			}
			mostrarDialogo = !mostrarDialogo;
		}
	}

	/*
	 * Utiles
	 */
	public Button getButton(int id) {
		return (Button) this.findViewById(R.id.enviar_mensaje_web_button);
	}

}
