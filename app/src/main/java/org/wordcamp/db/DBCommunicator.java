package org.wordcamp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.wordcamp.objects.wordcamp.WordCamps;

/**
 * Created by aagam on 27/1/15.
 */
public class DBCommunicator {

    public Context context;
    public WCSQLiteHelper helper;
    public SQLiteDatabase db;

    public Gson gson;
    public DBCommunicator(Context ctx){
        context = ctx;
        helper = new WCSQLiteHelper(context);
        gson  = new Gson();
    }

    public long addWC(WordCamps wc){

        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid",wc.getID());
        contentValues.put("data",gson.toJson(wc,WordCamps.class));
        return db.insert(WCSQLiteHelper.TABLE_WC,null,contentValues);
    }

    public WordCamps getWC(int id){
        Cursor cursor = db.query(WCSQLiteHelper.TABLE_WC, new String[] { "wcid",
                        "data"}, "wcid" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();

            String data = cursor.getString(1);
            WordCamps wc = gson.fromJson(data,WordCamps.class);
            cursor.close();
            return wc;

        }
        else{
            return null;
        }

    }

    public void start(){
        db = helper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }


}
