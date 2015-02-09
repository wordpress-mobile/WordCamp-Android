package org.wordcamp.utils;

import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by aagam on 29/1/15.
 */
public class WordCampUtils {

    public static boolean hasStartEndDate(WordCamps wc){

        return wc.getFoo().getStartDateYYYYMmDd().size()>0 && !wc.getFoo().getStartDateYYYYMmDd().get(0).equals("")
                && wc.getFoo().getEndDateYYYYMmDd().size()>0 && !wc.getFoo().getEndDateYYYYMmDd().get(0).equals("");
    }

    public static String getProperDate(WordCampDB wdb){
        Date d = new Date(Long.parseLong(wdb.getWc_start_date()) * 1000);
        Date d1 = new Date(Long.parseLong(wdb.getWc_end_date()) * 1000);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd, yyyy");

        return sdf.format(d)+" - "+sdf1.format(d1);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatProperTime(int time) {
        Date d = new Date((long)time*1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = 5*(Math.round(calendar.get(Calendar.MINUTE)/5));
        if(minutes==60){
            minutes=0;
            calendar.set(Calendar.HOUR,(calendar.get(Calendar.HOUR)+1));
        }
        calendar.set(Calendar.MINUTE,minutes);
        d = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(d);
    }

    public static int formatProperTimeHash(int time) {
        Date d = new Date((long)time*1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = 5*(Math.round(calendar.get(Calendar.MINUTE)/5));
        if(minutes==60){
            minutes=0;
            calendar.set(Calendar.HOUR,(calendar.get(Calendar.HOUR)+1));
        }
        calendar.set(Calendar.MINUTE,minutes);
        d = calendar.getTime();

        return d.hashCode();
    }
}
