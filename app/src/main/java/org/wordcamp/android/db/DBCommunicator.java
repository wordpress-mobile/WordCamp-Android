package org.wordcamp.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.wordcamp.android.R;
import org.wordcamp.android.objects.MiniSpeaker;
import org.wordcamp.android.objects.SessionDB;
import org.wordcamp.android.objects.SpeakerDB;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.objects.wordcamp.Session;
import org.wordcamp.android.objects.wordcamp.Speaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.wordcamp.android.utils.WCConstants.GRAVATAR_DEFAULT_SIZE;

/**
 * Created by aagam on 27/1/15.
 */
public class DBCommunicator {

    private final WCSQLiteHelper helper;
    private SQLiteDatabase db;
    private final Context ctx;
    private final String space = " ";
    private final Gson gson;

    public DBCommunicator(Context ctx) {
        helper = new WCSQLiteHelper(ctx);
        gson = new Gson();
        this.ctx = ctx;
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

    public long addSpeaker(Speaker sk, int wcid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid", wcid);
        contentValues.put("name", sk.getTitle().getRendered());
        contentValues.put("speaker_bio", sk.getContent().getRendered());
        contentValues.put("postid", sk.getId());
        contentValues.put("speaker_id", sk.getId());
        contentValues.put("gsonobject", gson.toJson(sk));
        String avatarUrl = sk.getAvatarUrls().get96();

        contentValues.put("gravatar", avatarUrl.equals("") ? "null" :
                avatarUrl.replace("s=96", "s=" + GRAVATAR_DEFAULT_SIZE));

        long id = db.insertWithOnConflict("speaker", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        if (id == -1) {
            id = db.update("speaker", contentValues, " wcid = ? AND postid = ?",
                    new String[]{String.valueOf(wcid), String.valueOf(sk.getId())});
        }

        if (sk.getEmbedded().getSessions().size() > 0)
            addSessionFromSpeaker(sk.getEmbedded().getSessions(), sk.getId(), wcid);

        return id;
    }

    private void addSessionFromSpeaker(List<Session> sessions, long spid, int wcid) {
        for (int i = 0; i < sessions.size(); i++) {
            Session ss = sessions.get(i);
            mapSessionToSingleSpeaker(wcid, ss.getId(), spid);
        }
    }

    private void mapSessionToSingleSpeaker(int wcid, long sid, long spid) {
        ContentValues values = new ContentValues();
        values.put("wcid", wcid);
        values.put("sessionid", sid);
        values.put("speakerid", spid);

        db.insertWithOnConflict("speakersessions", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public long addSession(Session ss, int wcid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("wcid", wcid);
        contentValues.put("title", ss.getTitle().getRendered());

        //Currently we are adding the sessions which are not getting fetched by Speakers API


        contentValues.put("time", ss.getMeta().getWcptSessionTime());

        contentValues.put("postid", ss.getId());
        if (ss.getEmbedded().getWpTerm() != null && ss.getEmbedded().getWpTerm().size() > 0
                && ss.getEmbedded().getWpTerm().get(ss.getEmbedded().getWpTerm().size()-1).size() > 0)
            contentValues.put("location", ss.getEmbedded().getWpTerm().get(ss.getEmbedded().getWpTerm().size()-1).get(0).getName());

        contentValues.put("category", ss.getMeta().getWcptSessionType());
        contentValues.put("gsonobject", gson.toJson(ss));

        long id = db.insertWithOnConflict("session", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        if (id == -1) {
            contentValues.remove("wcid");
            contentValues.remove("postid");
            id = db.update("session", contentValues, " wcid = ? AND postid = ?",
                    new String[]{String.valueOf(wcid), String.valueOf(ss.getId())});
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

    public List<WordCampDB> getAllMyWc() {
        Cursor cursor = db.rawQuery("SELECT * FROM wordcamp WHERE mywc = 1 ", null);

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

    public List<SessionDB> getFavoriteSessions(int wcid) {
        Cursor cursor = db.rawQuery("SELECT * from session WHERE mysession=1 AND wcid=" + wcid, null);
        List<SessionDB> sessionDBs = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(1);
                    int starttime = cursor.getInt(2);
                    int postid = cursor.getInt(3);
                    String category = cursor.getString(5);
                    String location = cursor.getString(4);
                    String lastscan = cursor.getString(6);
                    String gsonobject = cursor.getString(7);
                    sessionDBs.add(new SessionDB(wcid, postid, title, starttime, lastscan,
                            location, category, gsonobject, true));
                } while (cursor.moveToNext());
                cursor.close();

                Collections.sort(sessionDBs, new Comparator<SessionDB>() {
                    @Override
                    public int compare(SessionDB lhs, SessionDB rhs) {
                        return lhs.getTime() - rhs.getTime();
                    }
                });
                return sessionDBs;
            }
        }
        return sessionDBs;
    }

    public HashMap<Integer, String> getSpeakersforAllSessions(int wcid) {
        Cursor cursor = db.rawQuery("SELECT name, sessionid FROM speakersessions JOIN speaker" +
                " ON speaker.speaker_id = speakersessions.speakerid AND speaker.wcid=" + wcid + " " +
                "WHERE speakersessions.wcid=" + wcid + " ORDER BY sessionid ASC", null);

        HashMap<Integer, String> speakersEachSession = new HashMap<>();
        HashMap<Integer, Integer> speakersCount = new HashMap<>();

        int prevSessioID = -1;
        int counter = 0;

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String speakerName = cursor.getString(0);
                    int sessionid = cursor.getInt(1);
                    if (prevSessioID != sessionid) {
                        prevSessioID = sessionid;
                        counter = 1;
                        speakersCount.put(sessionid, counter);
                        speakersEachSession.put(sessionid, speakerName);
                    } else {
                        counter++;
                        speakersCount.put(sessionid, counter);
                        if (counter == 2) {
                            speakersEachSession.put(sessionid, speakerName + space +
                                    ctx.getString(R.string.and) + space + speakersEachSession.get(sessionid));
                        } else {
                            speakersEachSession.put(sessionid, speakerName);
                        }
                    }
                } while (cursor.moveToNext());
                cursor.close();

                for (Map.Entry<Integer, Integer> sCounts : speakersCount.entrySet()) {
                    int speakerscount = sCounts.getValue();

                    if (speakerscount > 2) {
                        int sessionid = sCounts.getKey();
                        String counterString = ctx.getResources()
                                .getString(R.string.multiple_speakers, speakerscount - 1);
                        String multipleSpeakers = speakersEachSession.get(sessionid) + " " +
                                counterString;
                        speakersEachSession.put(sessionid, multipleSpeakers);
                    }
                }

                return speakersEachSession;
            }
        }
        return speakersEachSession;
    }


}
