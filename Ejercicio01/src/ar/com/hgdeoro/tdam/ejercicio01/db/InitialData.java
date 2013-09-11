package ar.com.hgdeoro.tdam.ejercicio01.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class InitialData {

    private SQLiteDatabase db;

    public InitialData(SQLiteDatabase db) {
        this.db = db;
    }

    private void insert(long id, String text) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TEXTOS_COL_ID, Long.valueOf(id));
        cv.put(DBHelper.TEXTOS_COL_TEXTO, text);
        db.insert(DBHelper.TEXTOS_TABLE_NAME, DBHelper.TEXTOS_COL_ID, cv);
    }

    public void insertInitialData() {
        insert(1, "Un texto de ejemplo");
        insert(2, "Este ya es un texto un poco más largo");
        insert(3, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
                + "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad "
                + "minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
                + "aliquip ex ea commodo consequat. Duis aute irure dolor in "
                + "reprehenderit in voluptate velit esse cillum dolore eu fugiat "
                + "nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum. "
                + "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
                + "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad "
                + "minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
                + "aliquip ex ea commodo consequat. Duis aute irure dolor in "
                + "reprehenderit in voluptate velit esse cillum dolore eu fugiat "
                + "nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum. "
                + "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
                + "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad "
                + "minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
                + "aliquip ex ea commodo consequat. Duis aute irure dolor in "
                + "reprehenderit in voluptate velit esse cillum dolore eu fugiat "
                + "nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum. "
                + "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
                + "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad "
                + "minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
                + "aliquip ex ea commodo consequat. Duis aute irure dolor in "
                + "reprehenderit in voluptate velit esse cillum dolore eu fugiat "
                + "nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum. ");
    }
}
