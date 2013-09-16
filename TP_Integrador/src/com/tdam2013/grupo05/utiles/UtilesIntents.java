package com.tdam2013.grupo05.utiles;

import com.tdam2013.grupo05.AccionesSobreContactoActivity;
import com.tdam2013.grupo05.EnviarMensajeWebActivity;
import com.tdam2013.grupo05.HistorialActivity;
import com.tdam2013.grupo05.MostrarDetalleMensajeWebActivity;
import com.tdam2013.grupo05.RegistrarUsuarioActivity;
import com.tdam2013.grupo05.preferences.HistorialSettingsActivity;
import com.tdam2013.grupo05.preferences.ListaDeContactosSettingsActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class UtilesIntents {

    public static Intent getHistorialActivityIntent(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, HistorialActivity.class.getCanonicalName()));
        return intent;
    }

    public static Intent getRegistrarUsuarioActivityIntent(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, RegistrarUsuarioActivity.class
                .getCanonicalName()));
        return intent;
    }

    public static Intent getAccionesSobreContactoActivityIntent(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, AccionesSobreContactoActivity.class
                .getCanonicalName()));
        return intent;
    }

    public static Intent getEnviarMensajeWebActivityIntent(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, EnviarMensajeWebActivity.class
                .getCanonicalName()));
        return intent;
    }

    public static Intent getListaDeContactosSettingsActivity(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, ListaDeContactosSettingsActivity.class
                .getCanonicalName()));
        return intent;
    }

    public static Intent getHistorialSettingsActivity(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, HistorialSettingsActivity.class
                .getCanonicalName()));
        return intent;
    }

    public static Intent getMostrarDetalleMensajeWebActivity(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, MostrarDetalleMensajeWebActivity.class
                .getCanonicalName()));
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
