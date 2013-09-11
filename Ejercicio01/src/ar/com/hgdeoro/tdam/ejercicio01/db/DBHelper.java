package ar.com.hgdeoro.tdam.ejercicio01.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "textos";

    public static final String TEXTOS_TABLE_NAME = "textos";
    public static final String TEXTOS_COL_ID = "_id";
    public static final String TEXTOS_COL_TEXTO = "text_string";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TEXTOS_TABLE_NAME + " (" + TEXTOS_COL_ID
                + " INTEGER PRIMARY KEY , " + TEXTOS_COL_TEXTO + " TEXT)");

        new InitialData(db).insertInitialData();

        // FIXME: hace falta 'db.close();'?
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("DROP TABLE " + TEXTOS_TABLE_NAME);
            this.onCreate(db);
        }
    }

    /**
     * Devuelve cursor para acceder a todos los textos existentes.
     * 
     * @return
     */
    public Cursor obtenerTextos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TEXTOS_TABLE_NAME, new String[] { TEXTOS_COL_ID, TEXTOS_COL_TEXTO },
                null, null, null, null, null);
        return cur;
    }

    /**
     * Devuelve texto por id, o null si no se encontro.
     * 
     * @param id
     * @return
     */
    public String obtenerTextoById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        final String where = "" + TEXTOS_COL_TEXTO + " = ?";

        Cursor cur = db.query(TEXTOS_TABLE_NAME, new String[] { TEXTOS_COL_ID, TEXTOS_COL_TEXTO },
                where, new String[] { Long.toString(id) }, null, null, null);

        try {
            if (!cur.moveToFirst())
                return null;
            if (cur.moveToNext())
                throw new RuntimeException("Se obtuvo mas de un resultado al buscar por ID");

            return cur.getString(cur.getColumnIndexOrThrow(TEXTOS_COL_TEXTO));
        } finally {
            cur.close();
        }
    }

}
