package com.tdam2013.grupo05;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Utiles {

    public static Intent getHistorialActivityIntent(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx,
                "com.tdam2013.grupo05.AccionesSobreContactoActivity"));
        return intent;
    }

    public static Intent getRegistrarUsuarioActivityIntent(Context ctx) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ctx, "com.tdam2013.grupo05.RegistrarUsuarioActivity"));
        return intent;
    }

}
