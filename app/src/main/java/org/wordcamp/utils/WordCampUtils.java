package org.wordcamp.utils;

import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
