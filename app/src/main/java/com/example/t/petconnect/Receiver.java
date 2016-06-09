package com.example.t.petconnect;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vishwa on 5/16/2016.
 *
 * Handles the notification pop-up on user's notification drawer
 */
public class Receiver extends BroadcastReceiver {
    private NotificationManager manager;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.e("SomeService", "Receiving Broadcast, starting service");
        Intent intents = new Intent(context,MainActivity.class);
        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intents,PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Creates the notification with event reminder with notification sound
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("petconnect : Event Reminder")
                .setContentText("Hello World") // Get Event Name and Time
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        manager.notify(0,builder.build());
    }
}