package org.wordcamp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import org.wordcamp.objects.MiniSpeaker;
import org.wordcamp.objects.SessionDB;
import org.wordcamp.objects.SpeakerDB;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.speaker.Session;
import org.wordcamp.objects.speaker.SpeakerNew;
import org.wordcamp.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aagam on 27/1/15.
 */
public class DBCommunicator {

    private WCSQLiteHelper helper;
    private SQLiteDatabase db;


    private Gson gson;

    public DBCommunicator(Context ctx) {
        helper = new WCSQLiteHelper(ctx);
        gson = new Gson();
    }

    public void addAllNewWC(List<WordCampDB> wcList) {
        for (int i = 0; i < wcList.size(); i++) {
            WordCampDB wc = wcList.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("wcid", wc.getWc_id());
            contentValues.put("title", wc.getWc_title());
            contentValues.put("fromdate", wc.getWc_start_date());
            contentValues.put("todate", wc.getWc_end_date());
            contentValues.put("gsonobject", wc.getGson_object());
            contentValues.put("url", wc.getUrl());
            contentValues.put("twitter", wc.getTwitter());
            contentValues.put("featuredImageUrl", wc.getFeatureImageUrl());
            contentValues.put("venue", wc.getVenue());
            contentValues.put("location", wc.getLocation());
            contentValues.put("address", wc.getAddress());
            contentValues.put("about", wc.getAbout());

            long id = db.insertWithOnConflict(WCSQLiteHelper.TABLE_WC, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

            if (id == -1) {
                //update it
                contentValues.remove("wcid");
                db.update("wordcamp", contentValues, " wcid = ?",
                        new String[]{String.valueOf(wc.getWc_id())});
            }
        }
    }

    public void updateWC(WordCampDB wc) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", wc.getWc_title());
        contentValues.put("fromdate", wc.getWc_start_date());
        contentValues.put("todate", wc.getWc_end_date());
        contentValues.put("gsonobject", wc.getGson_object());
        contentValues.put("url", wc.getUrl());
        contentValues.put("twitter", wc.getTwitter());
        contentValues.put("featuredImageUrl", wc.getFeatureImageUrl());
        contentValues.put("venue", wc.getVenue());
        contentValues.put("location", wc.getLocation());
        contentValues.put("address", wc.getAddress());
        contentValues.put("about", wc.getAbout());

        db.update("wordcamp", contentValues, " wcid = ?",
                new String[]{String.valueOf(wc.getWc_id())});
    }

    public int addToMyWC(int wcid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mywc", 1);
        return db.update("wordcamp", contentValues, " wcid = ?",
                new String[]{String.valueOf(wcid)});
    }

    public int addToMySession(SessionDB sdb) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mysession", 1);
        return db.update("session", contentValues, " wcid = ? AND postid = ?",
                new String[]{String.valueOf(sdb.getWc_id()), String.valueOf(sdb.getPost_id())});
    }

    public void removeFromMySession(SessionDB sdb) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mysession", 0);
        db.update("session", contentValues, " wcid = ? AND postid = ?",
                new String[]{String.valueOf(sdb.getWc_id()), String.valueOf(sdb.getPost_id())});
    }

    public void removeFromMyWC(List<Integer> removedWCIDs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mywc", 0);
        for (int i = 0; i < removedWCIDs.size(); i++) {
            db.update("wordcamp", contentValues, " wcid = ?",
                    new String[]{String.valueOf(removedWCIDs.get(i))});
        }
    }

    public void removeFromMyWCSingle(int wcid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mywc", 0);
        db.update("wordcamp", contentValues, " wcid = ?",
                new String[]{String.valueOf(wcid)});
    }

    public long addSpeaker(SpeakerNew sk, int wcid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid", wcid);
        contentValues.put("name", sk.getTitle());
        contentValues.put("speaker_bio", sk.getContent());
        contentValues.put("postid", sk.getID());
        contentValues.put("speaker_id", sk.getID());
        contentValues.put("gsonobject", gson.toJson(sk));
        contentValues.put("gravatar", sk.getAvatar().equals("") ? "null" :
                sk.getAvatar().substring(0, sk.getAvatar().length() - 5) + "?s=120");

        long id = db.insertWithOnConflict("speaker", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        if (id == -1) {
            id = db.update("speaker", contentValues, " wcid = ? AND postid = ?",
                    new String[]{String.valueOf(wcid), String.valueOf(sk.getID())});
        }

        if (sk.getSessions().size() > 0)
            addSessionFromSpeaker(sk.getSessions(), sk.getID(), wcid);

        return id;
    }

    private void addSessionFromSpeaker(List<Session> sessions, int spid, int wcid) {
        for (int i = 0; i < sessions.size(); i++) {
            Session ss = sessions.get(i);

            /*HashMap<String, String> map = WordCampUtils.getTimeAndTypeSession(ss);

            ContentValues contentValues = new ContentValues();
            contentValues.put("wcid", wcid);
            contentValues.put("title", ss.getTitle());

            contentValues.put("time", map.get("_wcpt_session_time"));

            contentValues.put("postid", ss.getID());
            if (ss.getTerms()!=null && ss.getTerms().getWcbTrack().size() == 1)
                contentValues.put("location", ss.getTerms().getWcbTrack().get(0).getName());

            contentValues.put("category", map.get("_wcpt_session_type"));
            contentValues.put("gsonobject", gson.toJson(ss));

            long id = db.insertWithOnConflict("session", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

            if (id == -1) {
                contentValues.remove("wcid");
                contentValues.remove("postid");
                id = db.update("session", contentValues, " wcid = ? AND postid = ?",
                        new String[]{String.valueOf(wcid), String.valueOf(ss.getID())});
            }*/
            mapSessionToSingleSpeaker(wcid, ss.getID(), spid);
        }
    }

    private void mapSessionToSingleSpeaker(int wcid, int sid, int spid) {
        ContentValues values = new ContentValues();
        values.put("wcid", wcid);
        values.put("sessionid", sid);
        values.put("speakerid", spid);

        long id = db.insertWithOnConflict("speakersessions", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public long addSession(org.wordcamp.objects.speaker.Session ss, int wcid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid", wcid);
        contentValues.put("title", ss.getTitle());
        HashMap<String, String> map = WordCampUtils.getTimeAndTypeSession(ss);

        //Currently we are adding the sessions which are not getting fetched by Speakers API


        contentValues.put("time", map.get("_wcpt_session_time"));

        contentValues.put("postid", ss.getID());
        if (ss.getTerms() != null && ss.getTerms().getWcbTrack().size() == 1)
            contentValues.put("location", ss.getTerms().getWcbTrack().get(0).getName());

        contentValues.put("category", map.get("_wcpt_session_type"));
        contentValues.put("gsonobject", gson.toJson(ss));

        long id = db.insertWithOnConflict("session", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        if (id == -1) {
            contentValues.remove("wcid");
            contentValues.remove("postid");
            id = db.update("session", contentValues, " wcid = ? AND postid = ?",
                    new String[]{String.valueOf(wcid), String.valueOf(ss.getID())});
        }

        return id;
    }

    public WordCampDB getWC(int id) {

        Cursor cursor = db.rawQuery("SELECT * FROM wordcamp WHERE wcid=" + id, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String title = cursor.getString(1);
            String from = cursor.getString(2);
            String to = cursor.getString(3);
            String lastscanned = cursor.getString(4);
            String data = cursor.getString(5);
            String url = cursor.getString(6);
            String featureImageUrl = cursor.getString(7);
            int isMyWc = cursor.getInt(8);
            String twitter = cursor.getString(9);

            String address = cursor.getString(10);
            String venue = cursor.getString(11);
            String location = cursor.getString(12);
            String about = cursor.getString(13);
            cursor.close();
            return new WordCampDB(id, title, from, to, lastscanned, data, url, featureImageUrl
                    , isMyWc != 0, twitter, address, venue, location, about);
        } else {
            return null;
        }

    }

    public List<WordCampDB> getAllWc() {
        Cursor cursor = db.rawQuery("SELECT * FROM wordcamp ", null);

        if (cursor != null) {
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
                    int isMyWC = cursor.getInt(8);
                    String twitter = cursor.getString(9);

                    String address = cursor.getString(10);
                    String venue = cursor.getString(11);
                    String location = cursor.getString(12);
                    String about = cursor.getString(13);

                    wordCampDBList.add(new WordCampDB(id, title, from, to, lastscanned,
                            data, url, featuredImage, isMyWC != 0, twitter, address, venue, location, about));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return wordCampDBList;
        } else {
            return null;
        }
    }


    /**
     * Get the speaker by the post-id
     *
     * @param wc_id
     * @param id
     * @return Speaker's info
     */
    public SpeakerDB getSpeaker(int wc_id, int id) {

        Cursor cursor = db.rawQuery("SELECT * FROM speaker WHERE postid=" + id + " AND wcid=" + wc_id, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int wcid = cursor.getInt(0);
            String name = cursor.getString(1);
            String speakerbio = cursor.getString(3);
            int postid = cursor.getInt(4);
            String featuredimg = cursor.getString(5);
            String lastscan = cursor.getString(6);
            String gsonobject = cursor.getString(7);
            String gravatar = cursor.getString(8);

            cursor.close();
            return new SpeakerDB(wcid, name, postid, speakerbio, featuredimg, lastscan, gsonobject, gravatar);
        }
        return null;
    }

    public List<SpeakerDB> getAllSpeakers(int wcid) {
        Cursor cursor = db.rawQuery("SELECT * FROM speaker WHERE wcid=" + wcid, null);

        if (cursor != null) {
            List<SpeakerDB> speakerDBList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {

                    String name = cursor.getString(1);
                    String speakerbio = cursor.getString(3);
                    int postid = cursor.getInt(4);
                    String featuredimg = cursor.getString(5);
                    String lastscan = cursor.getString(6);
                    String gsonobject = cursor.getString(7);
                    String gravatar = cursor.getString(8);
                    speakerDBList.add(new SpeakerDB(wcid, name, postid, speakerbio, featuredimg, lastscan, gsonobject, gravatar));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return speakerDBList;

        }
        return null;

    }

    public SessionDB getSession(int wc_id, int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE wcid=" + wc_id + " AND postid=" + id, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int wcid = cursor.getInt(0);
            String title = cursor.getString(1);
            int time = cursor.getInt(2);
            String location = cursor.getString(4);
            String category = cursor.getString(5);
            String lastscan = cursor.getString(6);
            String gson = cursor.getString(7);
            boolean isMySession = cursor.getInt(8) == 1;

            cursor.close();
            return new SessionDB(wcid, id, title, time, lastscan, location, category, gson, isMySession);
        }
        return null;
    }

    public List<SessionDB> getAllSession(int wcid) {
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE wcid=" + wcid, null);
        List<SessionDB> sessionDBList = new ArrayList<>();
        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {

                    String title = cursor.getString(1);
                    int starttime = cursor.getInt(2);
                    int postid = cursor.getInt(3);
                    String category = cursor.getString(5);
                    String location = cursor.getString(4);
                    String lastscan = cursor.getString(6);
                    String gsonobject = cursor.getString(7);
                    boolean isMySession = cursor.getInt(8) == 1;
                    sessionDBList.add(new SessionDB(wcid, postid, title, starttime, lastscan,
                            location, category, gsonobject, isMySession));
                } while (cursor.moveToNext());

                cursor.close();

                Collections.sort(sessionDBList, new Comparator<SessionDB>() {
                    @Override
                    public int compare(SessionDB lhs, SessionDB rhs) {
                        return lhs.getTime() - rhs.getTime();
                    }
                });
                return sessionDBList;
            }
        }
        return sessionDBList;
    }

    public void start() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        if (db.isOpen())
            db.close();
    }

    public void restart() {
        if (db.isOpen())
            db.close();
        start();
    }


    public HashMap<String, Integer> getSpeakerSession(int wc_id, int speaker_id) {
        Cursor cursor = db.rawQuery("SELECT title, postid FROM speakersessions JOIN session" +
                " ON session.postid = speakersessions.sessionid AND session.wcid=" + wc_id + " " +
                "WHERE speakersessions.wcid=" + wc_id + " AND speakersessions.speakerid=" + speaker_id, null);

        if (cursor != null && cursor.getCount() > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            if (cursor.moveToFirst()) {
                do {
                    map.put(cursor.getString(0), cursor.getInt(1));
                } while (cursor.moveToNext());
                cursor.close();
                return map;
            }
        }
        return null;
    }

    public HashMap<String, MiniSpeaker> getSpeakersForSession(int wcid, int session_id) {
        Cursor cursor = db.rawQuery("SELECT name, speakerid, gravatar FROM speakersessions JOIN speaker" +
                " ON speaker.speaker_id = speakersessions.speakerid AND speaker.wcid=" + wcid + " " +
                "WHERE speakersessions.wcid=" + wcid + " AND speakersessions.sessionid=" + session_id, null);

        if (cursor != null && cursor.getCount() > 0) {
            HashMap<String, MiniSpeaker> map = new HashMap<>();
            if (cursor.moveToFirst()) {
                do {
                    map.put(cursor.getString(0), new MiniSpeaker(cursor.getString(0), cursor.getString(2)
                            , cursor.getInt(1)));
                } while (cursor.moveToNext());
                cursor.close();
                return map;
            }
        }
        return null;
    }

    public void addFeedbackUrl(int wcid, String url) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid", wcid);
        contentValues.put("url", url);
        db.insertWithOnConflict("feedback", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public String getFeedbackUrl(int wcid) {
        Cursor cursor = db.rawQuery("SELECT * from feedback WHERE wcid=" + wcid, null);
        if (cursor != null && cursor.getCount() > 0) {
            String url = "";
            if (cursor.moveToFirst()) {
                do {
                    url = cursor.getString(1);
                } while (cursor.moveToNext());
                cursor.close();
                return url;
            }
        }
        return null;
    }
}
