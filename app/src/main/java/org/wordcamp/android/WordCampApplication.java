package org.wordcamp.android;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.wordcamp.android.networking.WCHurlStack;

public class WordCampApplication extends Application {

    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this,new WCHurlStack());
    }
}
