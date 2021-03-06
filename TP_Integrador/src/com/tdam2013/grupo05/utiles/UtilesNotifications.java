package com.tdam2013.grupo05.utiles;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.tdam2013.grupo05.R;

public class UtilesNotifications {

	public static final int SEND_MESSAGE = 1;
	public static final int REGISTER_USER = 2;
	public static final int NETWORK_STATUS_CHANGE = 3;
	public static final int NEW_MESSAGE_RECEIVED = 4;

	public static NotificationManager getManager(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}

	public static void notify(Context ctx, String tickerText,
			String contentTitle, String contentText, int notificationId) {

		notify(ctx, tickerText, contentTitle, contentText, notificationId, 0,
				0, false);

	}

	public static void notifyWithIndeterminateProgress(Context ctx,
			String tickerText, String contentTitle, String contentText,
			int notificationId) {

		notify(ctx, tickerText, contentTitle, contentText, notificationId, 0,
				0, true);

	}

	public static void notify(Context ctx, String tickerText,
			String contentTitle, String contentText, int notificationId,
			int progressMax, int progressCurrent, boolean progressIndeterminate) {

		ctx = ctx.getApplicationContext();

		//
		// ----------
		//
		// Hay 2 implementaciones porque con la implementación inicial, luego de
		// mostrar y ocultar la notification bar un par de veces, esta se
		// bloquea oculta, y no puede volver a mostrarse.
		//
		// Al parecer, es un bug conocido:
		// - https://code.google.com/p/android/issues/detail?id=57129
		// -
		// https://stackoverflow.com/questions/17291699/android-emulator-only-opens-notification-bar-once
		//
		// ----------
		//
		// Luego de probar con una 3ra implementacion, hice la prueba de bajar y
		// subir el area de notificaciones en el emulador, y se bloquea! Aun
		// antes de mostrar ninguna notificacion. El problema definitivamente es
		// el emulador y no la app.
		//
		// ----------
		//

		/*
		 * PRIMERA IMPLEMENTACION
		 */

		// Notification notification = new Notification(
		// R.drawable.ic_launcher, tickerText,
		// System.currentTimeMillis());
		//
		// notification.setLatestEventInfo(ctx, contentTitle, contentText,
		// null);
		// notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//
		// getManager(ctx).notify(notificationId, notification);

		/*
		 * SEGUNDA IMPLEMENTACION
		 */

		NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker(tickerText);
		builder.setContentTitle(contentTitle);
		builder.setContentText(contentText);
		builder.setAutoCancel(true);

		builder.setProgress(progressMax, progressCurrent, progressIndeterminate);

		Notification notification = builder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		getManager(ctx).notify(notificationId, notification);

	}

}
