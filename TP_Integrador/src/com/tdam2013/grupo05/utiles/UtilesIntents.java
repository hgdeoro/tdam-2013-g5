package com.tdam2013.grupo05.utiles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tdam2013.grupo05.AccionesSobreContactoActivity;
import com.tdam2013.grupo05.EnviarMensajeWebActivity;
import com.tdam2013.grupo05.HistorialActivity;
import com.tdam2013.grupo05.MostrarDetalleMensajeWebActivity;
import com.tdam2013.grupo05.RegistrarUsuarioActivity;
import com.tdam2013.grupo05.preferences.HistorialSettingsActivity;
import com.tdam2013.grupo05.preferences.ListaDeContactosSettingsActivity;

/**
 * Clase utilitaria. Los metodos generan intents para lanzar activities.
 * 
 * @author Horacio G. de Oro
 *
 */
public class UtilesIntents {

	public static Intent getHistorialActivityIntent(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx, HistorialActivity.class
				.getCanonicalName()));
		return intent;
	}

	public static Intent getHistorialDeContactoActivityIntent(Context ctx,
			String contactName) {
		Intent intent = new Intent();
		intent.putExtra(HistorialActivity.INTENT_EXTRA__CONTACT_NAME,
				contactName);
		intent.setComponent(new ComponentName(ctx, HistorialActivity.class
				.getCanonicalName()));
		return intent;
	}

	public static Intent getRegistrarUsuarioActivityIntent(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				RegistrarUsuarioActivity.class.getCanonicalName()));
		return intent;
	}

	public static Intent getAccionesSobreContactoActivityIntent(Context ctx,
			long contactId, String displayName) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				AccionesSobreContactoActivity.class.getCanonicalName()));
		intent.putExtra(AccionesSobreContactoActivity.CONTACT_ID, contactId);
		intent.putExtra(AccionesSobreContactoActivity.DISPLAY_NAME, displayName);
		return intent;
	}

	public static Intent getEnviarMensajeWebActivityIntent(Context ctx,
			String to) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				EnviarMensajeWebActivity.class.getCanonicalName()));
		intent.putExtra(EnviarMensajeWebActivity.MSG_TO, to);
		return intent;
	}

	public static Intent getListaDeContactosSettingsActivity(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				ListaDeContactosSettingsActivity.class.getCanonicalName()));
		return intent;
	}

	public static Intent getHistorialSettingsActivity(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				HistorialSettingsActivity.class.getCanonicalName()));
		return intent;
	}

	public static Intent getMostrarDetalleMensajeWebActivity(Context ctx,
			long messageId, String contactName) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				MostrarDetalleMensajeWebActivity.class.getCanonicalName()));
		intent.putExtra(MostrarDetalleMensajeWebActivity.MESSAGE_WEB_ID,
				messageId);
		intent.putExtra(MostrarDetalleMensajeWebActivity.CONTACT_NAME, contactName);
		return intent;
	}

	/*
	 * Android
	 */
	public static Intent getCallPhoneIntent(String numero) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		// intent.setData(Uri.parse("tel:" + numero));
		intent.setData(Uri.fromParts("tel", numero, null));
		return intent;
	}

	/*
	 * Android
	 */
	public static Intent getSendSmsIntent(String numero) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.setData(Uri.parse("sms:" + numero));
		intent.setData(Uri.fromParts("sms", numero, null));
		return intent;
	}

	/*
	 * Android
	 */
	public static Intent getSendEmailIntent(String to) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.fromParts("mailto", to, null));
		return intent;
	}

}
