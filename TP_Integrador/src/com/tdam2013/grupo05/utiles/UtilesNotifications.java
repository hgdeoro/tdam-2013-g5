package com.tdam2013.grupo05.utiles;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.tdam2013.grupo05.R;

public class UtilesNotifications {

	public static NotificationManager getManager(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}

	@SuppressWarnings("deprecation")
	public static void notify(Context ctx, String tickerText,
			String contentTitle, String contentText, int notificationId) {

		// String tickerText = ctx.getString(R.string.xxxxxxxxxxxxxxxxx);

		Notification notification = new Notification(R.drawable.ic_launcher,
				tickerText, System.currentTimeMillis());

		// PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0,
		// Utilities.getOpenApplicationIntent(ctx), 0);

		// notification
		// .setLatestEventInfo(
		// ctx,
		// ctx.getString(R.string.title_notification_disconnection),
		// ctx.getString(R.string.message_notification_disconnection),
		// pendingIntent);

		notification.setLatestEventInfo(ctx, contentTitle, contentText, null);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		getManager(ctx).notify(notificationId, notification);
	}

}
