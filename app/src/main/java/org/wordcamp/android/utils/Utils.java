package org.wordcamp.android.utils;

import android.os.Build;

/**
 * Created by aagam on 3/3/15.
 */
public class Utils {

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static String getDeviceInfo() {
        return "\n\n Device info :- \n"
                + "API : " + Build.VERSION.SDK_INT + "\n "              // API Level
                + "Device : " + Build.DEVICE + "\n"                     // Device
                + "Manufacturer : " + Build.MANUFACTURER + "\n"         // Manufacturer
                + "Model : " + Build.MODEL + "\n"                       // Model
                + "Product : " + Build.PRODUCT;
    }
}
