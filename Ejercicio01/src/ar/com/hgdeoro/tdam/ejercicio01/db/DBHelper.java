package ar.com.hgdeoro.tdam.ejercicio01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        final String where = "" + TEXTOS_COL_ID + " = ?";

        Cursor cur = db.query(TEXTOS_TABLE_NAME, new String[] { TEXTOS_COL_ID, TEXTOS_COL_TEXTO },
                where, new String[] { Long.toString(id) }, null, null, null);

        try {
            if (!cur.moveToNext()) {
                return null;
            }

            if (!cur.isLast())
                Log.w("obtenerTextoById()", "cur.isLast() devolvio FALSE");

            for (String col : cur.getColumnNames())
                Log.i("obtenerTextoById()", "col: " + col);

            Log.i("obtenerTextoById()",
                    "getColumnIndexOrThrow(TEXTOS_COL_TEXTO): "
                            + cur.getColumnIndexOrThrow(TEXTOS_COL_TEXTO));

            return cur.getString(cur.getColumnIndexOrThrow(TEXTOS_COL_TEXTO));
        } finally {
            cur.close();
        }
    }

    /**
     * Inserta o actualiza texto. Insert -> devuelve ID.
     * 
     * @param id
     * @param texto
     */
    public long guardarTexto(long id, String texto) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TEXTOS_COL_TEXTO, texto);
        if (id == -1) {
            return db.insert(DBHelper.TEXTOS_TABLE_NAME, DBHelper.TEXTOS_COL_ID, cv);
        } else {
            final String where = "" + TEXTOS_COL_ID + " = ?";
            int count = db.update(DBHelper.TEXTOS_TABLE_NAME, cv, where,
                    new String[] { Long.toString(id) });
            // assert count== 1
            return -1;
        }

    }

}
