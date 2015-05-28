package org.wordcamp;


import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * The only purpose of this class is start Parse Crash reporting and Push service
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseCrashReporting.enable(this);
        Parse.initialize(this, BuildConfig.PARSE_APPKEY,
                BuildConfig.PARSE_TOKEN);


        ParsePush.subscribeInBackground("wc", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.e("push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("push", "failed to subscribe for push", e);
                }
            }
        });
    }

}
