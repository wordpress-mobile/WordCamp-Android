package org.wordcamp.android.notifs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.wordcamp.android.objects.SessionDB;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by aagam on 10/3/15.
 */
public class FavoriteSession {

    private Context context;

    public FavoriteSession(Context context) {
        this.context = context;
    }

    public void favoriteSession(SessionDB session) {
        Calendar calendar = Calendar.getInstance();
        long s = ((long) session.getTime()) * 1000;
        Date d = new Date(s);
        calendar.setTime(d);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.add(Calendar.MINUTE, -30);

        //Converting the time to Local TimeZone
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE));

        d = newCalendar.getTime();
        Date now = new Date();

        if (now.after(d)) {
            return;
        }

        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(session);
        manager.set(AlarmManager.RTC_WAKEUP, newCalendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * Creates a PendingIntent to be sent when the alarm for this session goes off.
     */
    private PendingIntent getPendingIntent(SessionDB ss) {
        Intent intent = new Intent();
        intent.putExtra("postid", ss.getPost_id());
        intent.putExtra("wcid", ss.getWc_id());
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
