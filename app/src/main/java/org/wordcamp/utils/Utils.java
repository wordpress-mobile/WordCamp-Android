package org.wordcamp.utils;

import android.os.Build;

/**
 * Created by aagam on 3/3/15.
 */
public class Utils {

    public static boolean isLollipop(){
        return Build.VERSION.SDK_INT>=21;
    }
}
