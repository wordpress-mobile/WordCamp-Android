package org.wordcamp;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

/**
 * The only purpose of this class is start Parse Crash reporting
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseCrashReporting.enable(this);
        Parse.initialize(this, BuildConfig.PARSE_APPKEY,
                BuildConfig.PARSE_TOKEN);
    }

}
