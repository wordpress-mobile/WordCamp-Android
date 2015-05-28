package org.wordcamp.objects;

import java.io.Serializable;

/**
 * Created by aagam on 28/1/15.
 */
public class SessionDB implements Serializable {

    private int wc_id;
    private int post_id;
    private String title;
    private int time;
    private String last_scanned_gmt;
    private String location;
    public String category;
    private String gson_object;
    public boolean isMySession;

    public SessionDB(int wc_id, int post_id, String title, int time, String last_scanned_gmt,
                     String location, String category, String gson_object, boolean isMySession) {
        this.wc_id = wc_id;
        this.post_id = post_id;
        this.title = title;
        this.time = time;
        this.last_scanned_gmt = last_scanned_gmt;
        this.location = location;
        this.category = category;
        this.gson_object = gson_object;
        this.isMySession = isMySession;
    }

    public int getWc_id() {
        return wc_id;
    }

    public void setWc_id(int wc_id) {
        this.wc_id = wc_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getLast_scanned_gmt() {
        return last_scanned_gmt;
    }

    public void setLast_scanned_gmt(String last_scanned_gmt) {
        this.last_scanned_gmt = last_scanned_gmt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGson_object() {
        return gson_object;
    }

    public void setGson_object(String gson_object) {
        this.gson_object = gson_object;
    }
}
