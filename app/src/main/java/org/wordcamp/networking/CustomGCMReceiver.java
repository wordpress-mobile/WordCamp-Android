package org.wordcamp.networking;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.wordcamp.R;

import java.util.Random;

/**
 * Created by aagam on 4/4/15.
 */
public class CustomGCMReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //if json sent from server is {"action":"org.wordcamp.UPDATES","type":"feedback","title":"WC Custom",
        // "descr":"Please give your feedback","link":"http://www.google.com"}

        Log.e("customgcm","received");
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String type = json.getString("type");
            String title = json.getString("title");
            String descr = json.getString("descr");
            String link = json.getString("link");

            showNotif(context,title,descr,link);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showNotif(Context context,String title,String descr,String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        browserIntent,
                        0
                );
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(descr)
                        .setContentIntent(resultPendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Random r = new Random();
        mNotifyMgr.notify(r.nextInt(), mBuilder.build());
    }
}
