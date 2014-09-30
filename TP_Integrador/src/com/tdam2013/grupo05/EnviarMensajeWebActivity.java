package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.utiles.UtilesContactos;
import com.tdam2013.grupo05.utiles.UtilesHttp;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;
import com.tdam2013.grupo05.utiles.UtilesNetwork;
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

	public static final int DIALOG_NO_CONNECTIVITY = 0;

	public static final int DIALOG_EMPTY_MESSAGE = 1;

	@SuppressWarnings("deprecation")
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

		if (!UtilesNetwork.isConnected(this.getApplicationContext())) {
			this.showDialog(DIALOG_NO_CONNECTIVITY);
		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_NO_CONNECTIVITY) {
			Dialog dialog = new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_no_connectivity_title)
					.setPositiveButton(R.string.dialog_no_connectivity_ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									EnviarMensajeWebActivity.this.finish();

								}
							})
					.setMessage(R.string.dialog_no_connectivity_message)
					.create();
			return dialog;

		} else if (id == DIALOG_EMPTY_MESSAGE) {
			Dialog dialog = new AlertDialog.Builder(this)
					.setTitle(R.string.dialog_message_empty_title)
					.setMessage(R.string.dialog_message_empty_message).create();
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

			// FIXME: on usar el "display name" del usuario!
			String destinatario = UtilesContactos
					.formatUsername(((TextView) findViewById(R.id.enviar_mensaje_web_destinatario))
							.getText().toString());

			final String mensaje = ((EditText) findViewById(R.id.enviar_mensaje_web_text))
					.getText().toString();

			if (mensaje.length() == 0) {
				showDialog(DIALOG_EMPTY_MESSAGE);
				return;
			}

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

			// TODO: use better texts
			String msg = getString(R.string.enviando_mensaje_web);
			UtilesNotifications.notifyWithIndeterminateProgress(ctx, msg, msg,
					msg, UtilesNotifications.SEND_MESSAGE);

			boolean ok;
			try {
				ok = utilesHttp.sendMessage(from, destinatario, mensaje);
			} catch (Exception e) {
				Log.w("sendMessage()", e);
				ok = false;
			}

			if (ok) {
				new Database(
						EnviarMensajeWebActivity.this.getApplicationContext())
						.insertSentMessage(destinatario, mensaje);

				// TODO: use better texts
				msg = getString(R.string.mensaje_web_enviado_ok);
				UtilesNotifications.notify(ctx, msg, msg, msg,
						UtilesNotifications.SEND_MESSAGE);

			} else {
				// TODO: use better texts
				msg = getString(R.string.mensaje_web_enviado_error);
				UtilesNotifications.notify(ctx, msg, msg, msg,
						UtilesNotifications.SEND_MESSAGE);
			}
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
