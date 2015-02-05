package org.wordcamp.objects;

import com.google.gson.Gson;

import org.wordcamp.objects.wordcamp.WordCamps;

import java.io.Serializable;

/**
 * Created by aagam on 28/1/15.
 */
public class WordCampDB implements Serializable {

    public int wc_id;
    public String wc_title;
    public String wc_start_date;
    public String wc_end_date;
    public String last_scanned_gmt;
    public String gson_object;
    public String url;
    public String featureImageUrl;
    public Gson gson;
    public boolean isMyWC=false;

    public WordCampDB(int wc_id, String wc_title, String wc_start_date, String wc_end_date,
                      String last_scanned_gmt, String gson_object,
                      String url, String featureImageUrl, boolean isMyWC) {
        this.wc_id = wc_id;
        this.wc_title = wc_title;
        this.wc_start_date = wc_start_date;
        this.wc_end_date = wc_end_date;
        this.last_scanned_gmt = last_scanned_gmt;
        this.gson_object = gson_object;
        this.url = url;
        this.featureImageUrl = featureImageUrl;
        this.isMyWC = isMyWC;
    }

    public WordCampDB(WordCamps wcs,String lastscan) {
        gson = new Gson();
        this.wc_id = wcs.getID();
        this.wc_title = wcs.getTitle();
        this.wc_start_date = wcs.getFoo().getStartDateYYYYMmDd().get(0);
        this.wc_end_date = wcs.getFoo().getEndDateYYYYMmDd().get(0);
        this.last_scanned_gmt = lastscan;
        this.gson_object = gson.toJson(wcs);
        if(wcs.getFoo().getURL().size()>0 && !wcs.getFoo().getURL().get(0).equals(""))
        {
            this.url = wcs.getFoo().getURL().get(0);
        }
        if(wcs.getFeaturedImage() != null){
            this.featureImageUrl = wcs.getFeaturedImage().getSource();
        }
    }

    public String getFeatureImageUrl() {
        return featureImageUrl;
    }

    public void setFeatureImageUrl(String featureImageUrl) {
        this.featureImageUrl = featureImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWc_id() {
        return wc_id;
    }

    public void setWc_id(int wc_id) {
        this.wc_id = wc_id;
    }

    public String getWc_title() {
        return wc_title;
    }

    public void setWc_title(String wc_title) {
        this.wc_title = wc_title;
    }

    public String getWc_start_date() {
        return wc_start_date;
    }

    public void setWc_start_date(String wc_start_date) {
        this.wc_start_date = wc_start_date;
    }

    public String getWc_end_date() {
        return wc_end_date;
    }

    public void setWc_end_date(String wc_end_date) {
        this.wc_end_date = wc_end_date;
    }

    public String getLast_scanned_gmt() {
        return last_scanned_gmt;
    }

    public void setLast_scanned_gmt(String last_scanned_gmt) {
        this.last_scanned_gmt = last_scanned_gmt;
    }

    public String getGson_object() {
        return gson_object;
    }

    public void setGson_object(String gson_object) {
        this.gson_object = gson_object;
    }
}
