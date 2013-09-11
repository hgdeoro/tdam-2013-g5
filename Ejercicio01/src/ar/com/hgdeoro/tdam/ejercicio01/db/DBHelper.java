package ar.com.hgdeoro.tdam.ejercicio01.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "textos";

    public static final String TEXTOS_TABLE_NAME = "textos";
    public static final String TEXTOS_COL_ID = "text_id";
    public static final String TEXTOS_COL_TEXTO = "text_string";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
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
    }

}
