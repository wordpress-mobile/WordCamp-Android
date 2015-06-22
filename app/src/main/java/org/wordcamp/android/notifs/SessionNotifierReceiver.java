package org.wordcamp.android.notifs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;

import org.wordcamp.android.BaseActivity;
import org.wordcamp.android.R;
import org.wordcamp.android.WordCampDetailActivity;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.objects.MiniSpeaker;
import org.wordcamp.android.objects.SessionDB;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.wcdetails.SessionDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aagam on 10/3/15.
 */
public class SessionNotifierReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int postid = intent.getIntExtra("postid", 0);
        int wcid = intent.getIntExtra("wcid", 0);
        DBCommunicator dbc = new DBCommunicator(context);
        dbc.start();
        SessionDB sdb = dbc.getSession(wcid, postid);
        final HashMap<String, MiniSpeaker> names = dbc.getSpeakersForSession(wcid, postid);
        ArrayList<String> speakerList = new ArrayList<>(names.keySet());
        WordCampDB wordCampDB = dbc.getWC(wcid);

        dbc.close();

        showStartNotification(sdb, context, speakerList, wordCampDB);

    }

    private static void showStartNotification(SessionDB session, final Context context, ArrayList<String> speakerList, WordCampDB wordCampDB) {
        Intent sessionDetailIntent = new Intent(context, SessionDetailsActivity.class);
        sessionDetailIntent.putExtra("session", session);

        Intent wcDetailIntent = new Intent(context, WordCampDetailActivity.class);
        wcDetailIntent.putExtra("wc", wordCampDB);

        Intent home = new Intent(context, BaseActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(home);
        stackBuilder.addNextIntent(wcDetailIntent);
        stackBuilder.addNextIntent(sessionDetailIntent);

        PendingIntent talkPendingIntent = stackBuilder.getPendingIntent(session.getPost_id(),
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_favorite_white_24dp);
        builder.setContentTitle(Html.fromHtml(session.getTitle()));
        if (session.getLocation() != null) {
            builder.setContentText("Starts in 30 minutes in " + session.getLocation());
        } else {
            builder.setContentText("Starts in 30 minutes");
        }
        if (speakerList.size() > 1)
            builder.setContentInfo(speakerList.get(0) + " & " + (speakerList.size() - 1) + " more");
        else
            builder.setContentInfo(speakerList.get(0));
        builder.setContentIntent(talkPendingIntent);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify("session", session.getPost_id(), notification);
    }
}
