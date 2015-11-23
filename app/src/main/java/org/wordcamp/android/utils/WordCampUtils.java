package org.wordcamp.android.utils;

import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.objects.speaker.Session;
import org.wordcamp.android.objects.wordcamp.PostMetum;
import org.wordcamp.android.objects.wordcamp.WordCampNew;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by aagam on 29/1/15.
 */
public class WordCampUtils {

    public static String getProperDate(WordCampDB wdb) {
        if (!wdb.getWc_end_date().isEmpty()) {
            Date d = new Date(Long.parseLong(wdb.getWc_start_date()) * 1000);
            Date d1 = new Date(Long.parseLong(wdb.getWc_end_date()) * 1000);
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
            return df.format(d) + " – " + df.format(d1);
        } else {
            Date d = new Date(Long.parseLong(wdb.getWc_start_date()) * 1000);
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
            return df.format(d);
        }
    }

    public static Date getProperDate(String s) {
        return new Date(Long.parseLong(s) * 1000);
    }

    public static String getFormattedDate(int date) {
        Date d = new Date((long) date * 1000);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public static String getFormattedTime(int date) {
        Date d = new Date((long) date * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = 5 * (Math.round(calendar.get(Calendar.MINUTE) / 5));
        if (minutes == 60) {
            minutes = 0;
            calendar.set(Calendar.HOUR, (calendar.get(Calendar.HOUR) + 1));
        }
        calendar.set(Calendar.MINUTE, minutes);
        d = calendar.getTime();
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public static int findFirstUpcomingFrag(List<WordCampDB> wcdb) {
        int counter = -1;
        if (wcdb != null) {
            long now = System.currentTimeMillis();

            for (int i = 0; i < wcdb.size(); i++) {
                long wcDate;
                if (!wcdb.get(i).getWc_end_date().isEmpty()) {
                    wcDate = Long.parseLong(wcdb.get(i).getWc_end_date()) * 1000;
                } else {
                    wcDate = Long.parseLong(wcdb.get(i).getWc_start_date()) * 1000;
                }
                long diffDate = (wcDate - now) / (1000 * 60 * 60 * 24);
                if (diffDate > -1) {
                    counter = i;
                    break;
                }
            }
        }
        return counter;
    }

    public static int formatProperTimeHash(int time) {
        Date d = new Date((long) time * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = 5 * (Math.round(calendar.get(Calendar.MINUTE) / 5));
        if (minutes == 60) {
            minutes = 0;
            calendar.set(Calendar.HOUR, (calendar.get(Calendar.HOUR) + 1));
        }
        calendar.set(Calendar.MINUTE, minutes);
        d = calendar.getTime();

        return d.hashCode();
    }

    public static HashMap<String, String> getTwitterAndUrl(WordCampNew wcn) {
        List<PostMetum> meta = wcn.getPostMeta();

        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < meta.size(); i++) {
            PostMetum metum = meta.get(i);
            map.put(metum.getKey(), metum.getValue());
        }

        return map;
    }

    public static HashMap<String, String> getTimeAndTypeSession(Session ss) {
        List<org.wordcamp.android.objects.speaker.PostMetum> meta = ss.getPostMeta();

        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < meta.size(); i++) {
            org.wordcamp.android.objects.speaker.PostMetum metum = meta.get(i);
            map.put(metum.getKey(), metum.getValue());
        }

        return map;
    }

    public static String generateHighResGravatarUrlFromDefault(String url) {
        return url.substring(0, url.length() - 6) + "?s=500";
    }
}
