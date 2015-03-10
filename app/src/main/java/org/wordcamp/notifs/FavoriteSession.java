package org.wordcamp.notifs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import org.wordcamp.objects.SessionDB;

/**
 * Created by aagam on 10/3/15.
 */
public class FavoriteSession {

    private Context context;
    public Gson gson;

    public FavoriteSession(Context context){
        this.context = context;
        gson  = new Gson();
    }

    public void favoriteSession(SessionDB session){
        long halfHour = session.getTime() - (30000 * 60);
        if (halfHour < System.currentTimeMillis()) {
            return;
        }

        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(session);
        manager.set(AlarmManager.RTC_WAKEUP, halfHour, pendingIntent);
    }

    /**
     * Creates a PendingIntent to be sent when the alarm for this session goes off.
     */
    private PendingIntent getPendingIntent(SessionDB ss) {
        Intent intent = new Intent();
        intent.putExtra("postid",ss.getPost_id());
        intent.putExtra("wcid",ss.getWc_id());
        intent.setClass(context, SessionNotifierReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
    }

    /**
     * Cancels any alarm scheduled for the given session.
     */
    public void unFavoriteSession(SessionDB sdb) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(sdb);
        manager.cancel(pendingIntent);
    }
}
