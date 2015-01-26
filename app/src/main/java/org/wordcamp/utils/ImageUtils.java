package org.wordcamp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by aagam on 23/1/15.
 */
public class ImageUtils {

    public static int getAspectRatio(Context ctx){
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width=0;

        if (android.os.Build.VERSION.SDK_INT >= 13){
            Point size = new Point();
            display.getSize(size);
            width = size.x;

        }
        else{
            width = display.getWidth();
        }

        return (9*width)/16;
    }

    public static int dpToPx(Context ctx, int dp){
        Resources r = ctx.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
