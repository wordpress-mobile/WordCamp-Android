package org.wordcamp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.wordcamp.objects.SessionDB;
import org.wordcamp.objects.SpeakerDB;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.session.Session;
import org.wordcamp.objects.speakers.Speakers;
import org.wordcamp.objects.wordcamp.WordCamps;
import org.wordcamp.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.List;

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
        contentValues.put("title",wc.getTitle());
        contentValues.put("fromdate",wc.getFoo().getStartDateYYYYMmDd().get(0));
        contentValues.put("todate",wc.getFoo().getEndDateYYYYMmDd().get(0));
        contentValues.put("gsonobject", gson.toJson(wc));

        return db.insert(WCSQLiteHelper.TABLE_WC,null,contentValues);
    }

    public void addAllNewWC(List<WordCampDB> wcList){
        for (int i = 0; i < wcList.size(); i++) {
            WordCampDB wc = wcList.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("wcid",wc.getWc_id());
            contentValues.put("title",wc.getWc_title());
            contentValues.put("fromdate",wc.getWc_start_date());
            contentValues.put("todate",wc.getWc_end_date());
            contentValues.put("gsonobject", wc.getGson_object());
            contentValues.put("url", wc.getUrl());
            contentValues.put("featuredImageUrl", wc.getFeatureImageUrl());
            long id = db.insert(WCSQLiteHelper.TABLE_WC,null,contentValues);

            if(id==-1){
                //update it
                contentValues.remove("wcid");
                db.update("wordcamp", contentValues, " wcid = ?",
                        new String[] { String.valueOf(wc.getWc_id()) });
            }
        }
    }

    public void updateWC(WordCamps wc){
        ContentValues contentValues = new ContentValues();
//        contentValues.put("wcid",wc.getID());
        contentValues.put("title",wc.getTitle());
        contentValues.put("fromdate",wc.getFoo().getStartDateYYYYMmDd().get(0));
        contentValues.put("todate",wc.getFoo().getEndDateYYYYMmDd().get(0));
        contentValues.put("gsonobject", gson.toJson(wc,WordCamps.class));
        if(wc.getFoo().getURL().size()>0)
            contentValues.put("url", wc.getFoo().getURL().get(0));

        if(wc.getFeaturedImage()!=null){
            contentValues.put("featuredImageUrl", wc.getFeaturedImage().getSource());
        }
        db.update("wordcamp", contentValues, " wcid = ?",
                new String[] { String.valueOf(wc.getID()) });
    }

    public long addSpeaker(Speakers sk, int wcid){
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid",wcid);
        contentValues.put("name", sk.getTitle());

        if(sk.getFoo().getPtUserId().size()>0 && !sk.getFoo().getPtUserId().get(0).equals("")){
            contentValues.put("speaker_id",sk.getFoo().getPtUserId().get(0));
        }

        contentValues.put("speaker_bio", sk.getContent());
        contentValues.put("postid", sk.getID());
        contentValues.put("gsonobject", gson.toJson(sk));
        if(sk.getFoo().getSpeakerEmail().size()>0 && !sk.getFoo().getSpeakerEmail().get(0).equals("")){
            String grav =sk.getFoo().getSpeakerEmail().get(0);
            String mdf = WordCampUtils.md5(grav);
            contentValues.put("gravatar","http://www.gravatar.com/avatar/"+ mdf);
        }


        long id =  db.insert("speaker",null,contentValues);


        if(id==-1){
            id = db.update("speaker", contentValues, " wcid = ? AND postid = ?",
                    new String[] { String.valueOf(wcid), String.valueOf(sk.getID())  });
        }

        return id;
    }


    public long addSession(Session ss, int wcid){
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid", wcid);
        contentValues.put("title", ss.getTitle());

        if(ss.getFoo().getWcptSessionTime().size()>0&& !ss.getFoo().getWcptSessionTime().get(0).equals(""))
            contentValues.put("time", ss.getFoo().getWcptSessionTime().get(0));

        contentValues.put("postid", ss.getID());
        if(ss.getTerms().getWcbTrack().size()>0)
            contentValues.put("location", ss.getTerms().getWcbTrack().get(0).getName());

        contentValues.put("category", ss.getFoo().getWcptSessionType().get(0));
        contentValues.put("gsonobject", gson.toJson(ss));

        return db.insert("session",null,contentValues);
    }


    private static final String DB_CREATE_WORDCAMP = "create table wordcamp ( wcid integer primary key," +
            " title text, from text, to text,lastscannedgmt text, gsonobject text, url text); ";

    public WordCampDB getWC(int id){

        Cursor cursor=db.rawQuery("SELECT * FROM wordcamp WHERE wcid="+id, null);

        if(cursor!=null){
            cursor.moveToFirst();
            String title = cursor.getString(1);
            String from = cursor.getString(2);
            String to = cursor.getString(3);
            String lastscanned = cursor.getString(4);
            String data = cursor.getString(5);
            String url = cursor.getString(6);
            String featureImageUrl = cursor.getString(7);
            cursor.close();
            return new WordCampDB(id,title,from,to,lastscanned,data,url,featureImageUrl);
        }
        else{
            return null;
        }

    }

    public List<WordCampDB> getAllWc(){
        Cursor cursor=db.rawQuery("SELECT * FROM wordcamp ", null);

        if(cursor!=null){
            List<WordCampDB> wordCampDBList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String from = cursor.getString(2);
                    String to = cursor.getString(3);
                    String lastscanned = cursor.getString(4);
                    String data = cursor.getString(5);
                    String url = cursor.getString(6);
                    String featuredImage = cursor.getString(7);
                    wordCampDBList.add(new WordCampDB(id,title,from,to,lastscanned,data,url,featuredImage));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return wordCampDBList;
        }
        else{
            return null;
        }
    }


    /**
     * Get the speaker by the post-id
     * @param id
     * @return  Speaker's info
     */
    public SpeakerDB getSpeaker(int id){

        Cursor cursor=db.rawQuery("SELECT * FROM speaker WHERE postid="+id, null);

        if(cursor!=null){
            cursor.moveToFirst();
            int wcid = cursor.getInt(0);
            String name = cursor.getString(1);
            int speakerid = cursor.getInt(2);
            String speakerbio = cursor.getString(3);
            int postid = cursor.getInt(4);
            String featuredimg = cursor.getString(5);
            String lastscan = cursor.getString(6);
            String gsonobject = cursor.getString(7);
            String gravatar  = cursor.getString(8);

            cursor.close();
            return new SpeakerDB(wcid,name,speakerid,postid,speakerbio,featuredimg,lastscan,gsonobject,gravatar);
        }
        return null;
    }

    public static final String DB_CREATE_SPEAKER = "create table speaker ( wcid integer, name text, " +
            "speaker_id int, speaker_bio text, postid int, featuredimage text, lastscannedgmt text," +
            " gsonobject text); ";

    public List<SpeakerDB> getAllSpeakers(int wcid){
        Cursor cursor=db.rawQuery("SELECT * FROM speaker WHERE wcid="+wcid, null);

        if(cursor!=null){
            List<SpeakerDB> speakerDBList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {

                    String name = cursor.getString(1);
                    int speakerid = cursor.getInt(2);
                    String speakerbio = cursor.getString(3);
                    int postid = cursor.getInt(4);
                    String featuredimg = cursor.getString(5);
                    String lastscan = cursor.getString(6);
                    String gsonobject = cursor.getString(7);
                    String gravatar  = cursor.getString(8);
                    speakerDBList.add(new SpeakerDB(wcid,name,speakerid,postid,speakerbio,featuredimg,lastscan,gsonobject,gravatar));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return speakerDBList;

        }
        return null;

    }

    public SessionDB getSession(int id){
        Cursor cursor=db.rawQuery("SELECT * FROM session WHERE postid="+id, null);

        if(cursor!=null){
            cursor.moveToFirst();
            int wcid = cursor.getInt(0);
            String title = cursor.getString(1);
            int time = cursor.getInt(2);
            String location = cursor.getString(4);
            String category = cursor.getString(5);
            String lastscan = cursor.getString(6);
            String gson = cursor.getString(7);

            return new SessionDB(wcid,id,title,time,lastscan,location,category,gson);
        }

        return null;
    }

    public List<SessionDB> getAllSession(int wcid) {
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE wcid=" + wcid, null);

        if (cursor != null) {
            List<SessionDB> sessionDBList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {

                    String title = cursor.getString(1);
                    int starttime = cursor.getInt(2);
                    int postid = cursor.getInt(3);
                    String category = cursor.getString(5);
                    String location = cursor.getString(4);
                    String lastscan = cursor.getString(6);
                    String gsonobject = cursor.getString(7);

                    sessionDBList.add(new SessionDB(wcid, postid, title, starttime, lastscan,
                            location, category, gsonobject));
                } while (cursor.moveToNext());

                cursor.close();
                return sessionDBList;

            }

        }
        return null;
    }




    public void start(){
        db = helper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }


}
