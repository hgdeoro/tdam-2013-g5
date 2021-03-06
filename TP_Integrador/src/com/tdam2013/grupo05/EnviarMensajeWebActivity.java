package com.tdam2013.grupo05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tdam2013.grupo05.db.Database;
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

	// Datos para poder volver a AccionesSobreContactoActivity
	private Long contactId = null;
	private String displayName = null;

	// Nombre de usuario del destinatario del mensaje
	private String msgTo = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enviar_mensaje_web_activity);

		msgTo = this.getIntent().getExtras()
				.getString(EnviarMensajeWebActivity.MSG_TO);

		// Reusamos constantes de AccionesSobreContactoActivity
		contactId = this.getIntent().getExtras()
				.getLong(AccionesSobreContactoFragment.CONTACT_ID);
		displayName = this.getIntent().getExtras()
				.getString(AccionesSobreContactoFragment.DISPLAY_NAME);

		if (msgTo == null) {
			// No conocemos el username del contacto. Lo solicitamos

			this.startActivity(IngresarUsuarioDeContactoActivity
					.getIngresarUsuarioDeContactoActivity(this, contactId,
							displayName));

			return;

		}

		getTextViewDestinatario().setText(displayName);

		/*
		 * Listeners
		 */
		getEnviarMensajeWebButton().setOnClickListener(
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
	private class EnviarMensajeWebOnClickListener implements
			View.OnClickListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {

			final String mensaje = getMensajeWeb();

			if (mensaje.length() == 0) {
				showDialog(DIALOG_EMPTY_MESSAGE);
				return;
			}

			final AsyncTask<Object, Void, Void> task = new SendMessageTask();
			final Context ctx = EnviarMensajeWebActivity.this
					.getApplicationContext();
			final String from = UtilesMensajesWeb
					.getUsername(EnviarMensajeWebActivity.this);

			task.execute(ctx, from, msgTo, mensaje);

			finish();

		}
	}

	private class SendMessageTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {

			final Context ctx = (Context) params[0];
			final String from = (String) params[1];
			final String destinatario = (String) params[2];
			final String mensaje = (String) params[3];

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					UtilesNotifications.notifyWithIndeterminateProgress(ctx,
							getString(R.string.enviando_mensaje_web),
							getString(R.string.enviando_mensaje_web),
							getString(R.string.enviando_mensaje_web),
							UtilesNotifications.SEND_MESSAGE);

				}

			});

			boolean ok;
			try {
				ok = utilesHttp.sendMessage(from, destinatario, mensaje);
			} catch (Exception e) {
				Log.w("sendMessage()", e);
				ok = false;
			}

			if (ok) {

				try {
					Database.getDatabase(
							EnviarMensajeWebActivity.this
									.getApplicationContext())
							.insertSentMessage(destinatario, mensaje);

				} catch (SQLiteException sqle) {

					sqle.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						UtilesNotifications.notify(ctx,
								getString(R.string.mensaje_web_enviado_ok),
								getString(R.string.mensaje_web_enviado_ok),
								getString(R.string.mensaje_web_enviado_ok),
								UtilesNotifications.SEND_MESSAGE);

					}

				});

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						UtilesNotifications.notify(ctx,
								getString(R.string.mensaje_web_enviado_error),
								getString(R.string.mensaje_web_enviado_error),
								getString(R.string.mensaje_web_enviado_error),
								UtilesNotifications.SEND_MESSAGE);

					}

				});

			}
			return null;
		}
	}

	/*
	 * Utiles
	 */
	private Button getEnviarMensajeWebButton() {
		return (Button) this.findViewById(R.id.enviar_mensaje_web_button);
	}

	/** Devuelve string en EditText, o sea, el texto del mensaje a enviar */
	private String getMensajeWeb() {
		return ((EditText) findViewById(R.id.enviar_mensaje_web_text))
				.getText().toString().trim();
	}

	private TextView getTextViewDestinatario() {
		return ((TextView) findViewById(R.id.enviar_mensaje_web_destinatario));
	}

	public static Intent getEnviarMensajeWebActivityIntent(Context ctx,
			String to, Long contactId, String displayName) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				EnviarMensajeWebActivity.class.getCanonicalName()));
		intent.putExtra(EnviarMensajeWebActivity.MSG_TO, to);
		intent.putExtra(AccionesSobreContactoFragment.CONTACT_ID, contactId);
		intent.putExtra(AccionesSobreContactoFragment.DISPLAY_NAME,
				displayName);
		return intent;
	}

}
