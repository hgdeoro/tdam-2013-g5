package com.tdam2013.grupo05.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tdam2013.grupo05.services.MensajeWebPollService;

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Toast.makeText(context, "BootCompletedBroadcastReceiver.onReceive()",
				Toast.LENGTH_SHORT).show();

		context.startService(MensajeWebPollService
				.getMensajeWebPollServiceForStartPolling(context));

	}

}
