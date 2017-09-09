package com.example.samyuktha.mediamemories;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by samyuktha on 9/6/2017.
 */

public class AlarmReceiver1 extends BroadcastReceiver {

    int MID=1;
    @Override
    public void onReceive(Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, Main2Activity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(android.R.drawable.btn_plus)
                .setContentTitle("Media Memories")
                .setContentText("you have these pictures clicked")
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent);
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;
    }
}
