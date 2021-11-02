package com.example.iinspector.SendNotificationPack;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFireBaseMessagingService extends FirebaseMessagingService {
    String id ="chanel_1";
    String description ="123";
    int importance = NotificationManager.IMPORTANCE_LOW;

    String title,message;
    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    final long[] VIBRATE_PATTERN    = {0, 1000};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");

        NotificationChannel channel = new NotificationChannel(id, "123", importance);//Generating channel
        channel.enableVibration(true);
        channel.enableLights(true);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getApplicationContext(),id)

                .setContentTitle(title)
                .setContentText(message)
                .setSound(soundUri)
                .setVibrate(new long[] {2000})

//                .setLights(Color.RED, 3000, 3000)
//                .setVibrate(VIBRATE_PATTERN)
//                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
    }

}
