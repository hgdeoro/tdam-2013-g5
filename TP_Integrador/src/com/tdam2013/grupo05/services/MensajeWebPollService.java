package com.tdam2013.grupo05.services;

import java.io.IOException;
import java.lang.Thread.State;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tdam2013.grupo05.MostrarDetalleMensajeWebActivity;
import com.tdam2013.grupo05.R;
import com.tdam2013.grupo05.db.Database;
import com.tdam2013.grupo05.modelos.MensajeWeb;
import com.tdam2013.grupo05.utiles.UtilesHttp;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;
import com.tdam2013.grupo05.utiles.UtilesNetwork;
import com.tdam2013.grupo05.utiles.UtilesNotifications;

//
// https://developer.android.com/guide/components/services.html
//

/**
 * Servicio Android, hace polling del webservice en busca de mensajes
 *
 */
public class MensajeWebPollService extends Service {

	private static final String PREFERENCE_ULTIMO_TIMESTAMP = "tdam_ultimo_timestamp_mensajes_web";

	private Thread pollThread;
	private PollRunnable pollRunnable;
	private UtilesHttp utilesHttp = new UtilesHttp();

	private void debug(String msg) {
		Log.d("MensajeWebPollService", msg);
	}

	class PollRunnable implements Runnable {

		private boolean running = true;

		public void stop() {
			running = false;
		}

		private void pollWebService() {
			MensajeWebPollService.this.debug("Iniciando pollWebService()");

			final String username = UtilesMensajesWeb
					.getUsername(MensajeWebPollService.this);

			MensajeWebPollService.this.debug("pollWebService(): buscando "
					+ "mensajes para usuario '" + username + "'");

			if (username == null) {
				MensajeWebPollService.this
						.debug("No se buscaran mensajes. Usuario es null.");
				return;
			}

			try {
				List<MensajeWeb> mensajes = utilesHttp.getAllMessages(username,
						obtenerUltimoTimestamp());

				if (mensajes == null)
					return;

				procesarMensajes(mensajes);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			MensajeWebPollService.this.debug("FIN: pollWebService()");
		}

		private void procesarMensajes(List<MensajeWeb> mensajes) {
			MensajeWebPollService.this.debug("Se recibieron " + mensajes.size()
					+ " mensajes");
			for (MensajeWeb mensaje : mensajes) {
				procesarMensaje(mensaje);
			}

			if (!mensajes.isEmpty()) {
				MensajeWeb ultimo = mensajes.get(mensajes.size() - 1);
				String ultimoTimestamp = ultimo.getTimestamp();
				guardarUltimoTimestamp(ultimoTimestamp);
			}

		}

		/** Guarda timestampe del ultimo mensaje recibido */
		private void guardarUltimoTimestamp(String ultimoTimestamp) {
			SharedPreferences sharedPref = PreferenceManager
					.getDefaultSharedPreferences(MensajeWebPollService.this);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(PREFERENCE_ULTIMO_TIMESTAMP, ultimoTimestamp);
			editor.commit();
			Log.d("guardarUltimoTimestamp()", "" + ultimoTimestamp);
		}

		/**
		 * Devuelve timestamp del ultimo mensaje obtenido. Si no existe (porque
		 * es la primera vez que se ejecuta o nunca se ha recibido un mensaje)
		 * devuelve fecha muy vieja.
		 */
		private String obtenerUltimoTimestamp() {
			SharedPreferences sharedPref = PreferenceManager
					.getDefaultSharedPreferences(MensajeWebPollService.this);
			String ultimoTimestamp = sharedPref.getString(
					PREFERENCE_ULTIMO_TIMESTAMP, "01/01/1970 00:00:00");
			Log.d("obtenerUltimoTimestamp()", "" + ultimoTimestamp);
			return ultimoTimestamp;
		}

		private void procesarMensaje(MensajeWeb mensaje) {

			MensajeWebPollService.this
					.debug("Mensaje: " + mensaje.getMensaje());

			Database db = Database.getDatabase(MensajeWebPollService.this);
			Date timestamp;
			try {
				timestamp = mensaje.getTimestampAsDate();
			} catch (ParseException e) {
				// No se pudo parsear fecha -> usamos actual
				e.printStackTrace();
				timestamp = new Date();
			}

			Long messageId = db.insertReceivedMessage(mensaje.getUser(),
					mensaje.getMensaje(), timestamp);

			notifyNewMessage(mensaje, messageId);

		}

		private void notifyNewMessage(MensajeWeb mensaje, Long messageId) {

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					MensajeWebPollService.this)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle("Nuevo mensaje")
					.setContentText("Nuevo mensaje de " + mensaje.getUser());

			Intent intent = MostrarDetalleMensajeWebActivity
					.getMostrarDetalleMensajeWebActivity(
							MensajeWebPollService.this, messageId,
							mensaje.getUser());

			mBuilder.setContentIntent(PendingIntent.getActivity(
					getApplicationContext(), 0, intent, 0));
			mBuilder.setAutoCancel(true);
			mBuilder.setProgress(100, 100, false);

			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			// mId allows you to update the notification later on.
			mNotificationManager.notify(
					UtilesNotifications.NEW_MESSAGE_RECEIVED, mBuilder.build());

		}

		@Override
		public void run() {
			MensajeWebPollService.this.debug("PollRunnable.run(): inicianndo");
			while (running
					&& UtilesNetwork.isConnected(MensajeWebPollService.this)) {

				MensajeWebPollService.this
						.debug("PollRunnable.run(): Iniciando poll");
				pollWebService();
				MensajeWebPollService.this
						.debug("PollRunnable.run(): Poll finalizado");

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					MensajeWebPollService.this
							.debug("PollRunnable.run(): Poll genero error");
					e.printStackTrace();
				}
			}
			running = false;
			MensajeWebPollService.this.debug("PollRunnable.run(): saliendo");
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		debug("onStartCommand()");

		if (pollThread != null) {
			// Si el servicio/thread ya esta andando, salimos.
			if (pollThread.getState() == State.TERMINATED) {
				pollRunnable = null;
				pollThread = null;
			} else {
				return START_STICKY;
			}
		}

		pollRunnable = new PollRunnable();
		pollThread = new Thread(pollRunnable);
		pollThread.start();

		// Toast.makeText(getApplicationContext(),
		// "MensajeWebPollService.onStartCommand()", Toast.LENGTH_SHORT)
		// .show();

		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		this.debug("onBind() llamado");
		return null;
	}

	@Override
	public void onCreate() {
		this.debug("onCreate() llamado");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (pollRunnable != null)
			pollRunnable.stop();

		this.debug("onDestroy() llamado");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		this.debug("onUnbind() llamado");
		return super.onUnbind(intent);
	}

	public static Intent getMensajeWebPollServiceForStartPolling(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx, MensajeWebPollService.class
				.getCanonicalName()));
		// TODO: agregar dato extra para indicar que es para INICIAR POLLING
		return intent;
	}

}
