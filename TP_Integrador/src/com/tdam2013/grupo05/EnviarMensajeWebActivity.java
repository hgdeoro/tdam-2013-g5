package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tdam2013.grupo05.utiles.UtilesHttp;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;
import com.tdam2013.grupo05.utiles.UtilesNotifications;

public class EnviarMensajeWebActivity extends Activity {

	/**
	 * Key to use in Intent.extra to declare destination of the message
	 */
	public static final String MSG_TO = "MSG_TO";

	/**
	 * Instancia de Utileshttp
	 */
	public static final UtilesHttp utilesHttp = new UtilesHttp();

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

			AsyncTask<Object, Void, Void> task = new SendMessageTask();
			task.execute(EnviarMensajeWebActivity.this.getApplicationContext(),
					UtilesMensajesWeb
							.getUsername(EnviarMensajeWebActivity.this),
					destinatario, mensaje);

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

			// FIXME: remove hardcoded strings & use better texts
			UtilesNotifications.notifyWithIndeterminateProgress(ctx,
					"Enviando mensaje...", "Enviando mensaje...",
					"Enviando mensaje...", UtilesNotifications.SEND_MESSAGE);

			boolean ok;
			try {
				ok = utilesHttp.sendMessage(from, destinatario, mensaje);
			} catch (Exception e) {
				Log.w("sendMessage()", e);
				ok = false;
			}

			if (ok)
				// FIXME: remove hardcoded strings & use better texts
				UtilesNotifications.notify(ctx,
						"El mensaje fue enviado correctamente",
						"El mensaje fue enviado correctamente",
						"El mensaje fue enviado correctamente",
						UtilesNotifications.SEND_MESSAGE);
			else
				// FIXME: remove hardcoded strings & use better texts
				UtilesNotifications.notify(ctx,
						"ERROR: el mensaje no fue enviado",
						"ERROR: el mensaje no fue enviado",
						"ERROR: el mensaje no fue enviado",
						UtilesNotifications.SEND_MESSAGE);

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
