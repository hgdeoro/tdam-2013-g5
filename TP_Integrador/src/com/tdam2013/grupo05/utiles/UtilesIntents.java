package com.tdam2013.grupo05.utiles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tdam2013.grupo05.AccionesSobreContactoActivity;
import com.tdam2013.grupo05.EnviarMensajeWebActivity;
import com.tdam2013.grupo05.IngresarUsuarioDeContactoActivity;
import com.tdam2013.grupo05.RegistrarUsuarioActivity;
import com.tdam2013.grupo05.preferences.HistorialSettingsActivity;
import com.tdam2013.grupo05.preferences.ListaDeContactosSettingsActivity;
import com.tdam2013.grupo05.services.MensajeWebPollService;

/**
 * Clase utilitaria. Los metodos generan intents para lanzar activities.
 * 
 * @author Horacio G. de Oro
 *
 */
public class UtilesIntents {

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
			String to, Long contactId, String displayName) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				EnviarMensajeWebActivity.class.getCanonicalName()));
		intent.putExtra(EnviarMensajeWebActivity.MSG_TO, to);
		intent.putExtra(AccionesSobreContactoActivity.CONTACT_ID, contactId);
		intent.putExtra(AccionesSobreContactoActivity.DISPLAY_NAME, displayName);
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

	public static Intent getIngresarUsuarioDeContactoActivity(Context ctx,
			long contactId, String displayName) {
		Intent intent = new Intent();

		intent.setComponent(new ComponentName(ctx,
				IngresarUsuarioDeContactoActivity.class.getCanonicalName()));

		intent.putExtra(AccionesSobreContactoActivity.CONTACT_ID, contactId);
		intent.putExtra(AccionesSobreContactoActivity.DISPLAY_NAME, displayName);

		return intent;
	}

	/**
	 * Devuelve Intent para iniciar servicio de polling.
	 */
	public static Intent getMensajeWebPollServiceForStartPolling(Context ctx) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx, MensajeWebPollService.class
				.getCanonicalName()));
		// FIXME: agregar dato extra para indicar que es para INICIAR POLLING
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
