package com.tdam2013.grupo05.services;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.tdam2013.grupo05.modelos.MensajeWeb;
import com.tdam2013.grupo05.utiles.UtilesHttp;
import com.tdam2013.grupo05.utiles.UtilesMensajesWeb;
import com.tdam2013.grupo05.utiles.UtilesNetwork;

//
// https://developer.android.com/guide/components/services.html
//

/**
 * Servicio Android, hace polling del webservice en busca de mensajes
 *
 */
public class MensajeWebPollService extends Service {

	private Thread pollThread;
	private PollRunnable pollRunnable;
	private UtilesHttp utilesHttp = new UtilesHttp();

	private void info(String msg) {
		Log.i("MensajeWebPollService", msg);
	}

	class PollRunnable implements Runnable {

		private boolean running = true;

		public void stop() {
			running = false;
		}

		private void pollWebService() {
			MensajeWebPollService.this.info("Iniciando pollWebService()");

			final String username = UtilesMensajesWeb
					.getUsername(MensajeWebPollService.this);

			MensajeWebPollService.this.info("pollWebService(): buscando "
					+ "mensajes para usuario '" + username + "'");

			try {
				List<MensajeWeb> mensajes = utilesHttp.getAllMessages(username);
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
			MensajeWebPollService.this.info("FIN: pollWebService()");
		}

		private void procesarMensajes(List<MensajeWeb> mensajes) {
			MensajeWebPollService.this.info("Se recibieron " + mensajes.size()
					+ " mensajes");
			for (MensajeWeb mensaje : mensajes) {
				procesarMensaje(mensaje);
			}
		}

		private void procesarMensaje(MensajeWeb mensaje) {
			// FIXME: enviar notificacion de mensaje recibido
			MensajeWebPollService.this.info("Mensaje: " + mensaje.getMensaje());
		}

		@Override
		public void run() {
			MensajeWebPollService.this.info("PollRunnable.run(): inicianndo");
			while (running
					&& UtilesNetwork.isConnected(MensajeWebPollService.this)) {
				try {
					pollWebService();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// FIXME: será lo correcto salir del loop en caso que se
					// produzca esta excepcion?
					e.printStackTrace();
				}
			}
			// Ya que podemos estar saliendo porque no hay conectividad, por las
			// dudas seteamos running
			running = false;
			MensajeWebPollService.this.info("PollRunnable.run(): saliendo");
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		info("onStartCommand()");

		if (pollThread != null) {
			// Si el servicio/thread ya esta andando, salimos.
			if (pollThread.getState() == State.TERMINATED) {
				pollRunnable = null;
				pollThread = null;
			} else {
				// If we get killed, after returning from here, restart
				// FIXME: esta bien devolver START_STICKY cuando el servicio ya
				// esta andando? En realidad, en estos casos, simplemente
				// hay que ignorar la llamada a este metodo
				return START_STICKY;
			}
		}

		pollRunnable = new PollRunnable();
		pollThread = new Thread(pollRunnable);
		pollThread.start();

		Toast.makeText(getBaseContext(),
				"MensajeWebPollService.onStartCommand()", Toast.LENGTH_SHORT)
				.show();

		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		this.info("onBind() llamado");
		return null;
	}

	@Override
	public void onCreate() {
		this.info("onCreate() llamado");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (pollRunnable != null)
			pollRunnable.stop();

		this.info("onDestroy() llamado");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		this.info("onUnbind() llamado");
		return super.onUnbind(intent);
	}

}
