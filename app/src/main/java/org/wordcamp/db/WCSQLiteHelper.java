package org.wordcamp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aagam on 27/1/15.
 */
public class WCSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "wordcamp.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_WC = "wordcamp";
    private static final String DB_CREATE = "create table wordcamp ( wcid integer primary key," +
            " data text); ";

    public WCSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
