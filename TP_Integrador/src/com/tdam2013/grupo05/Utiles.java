package com.tdam2013.grupo05;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Utiles {

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

}
