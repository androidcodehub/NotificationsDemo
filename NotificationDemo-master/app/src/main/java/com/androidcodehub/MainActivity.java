package com.androidcodehub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;


public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    public void sendNotification(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendNotification_O();
        } else {
            sendNotification_N();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification_O() {
        intent = new Intent(MainActivity.this, LoginActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(this, 0, intent, 0);
        String id = "channel_1";
        String description = "123";
        NotificationChannel mChannel = new NotificationChannel(id, "123", NotificationManager.IMPORTANCE_HIGH);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mChannel.setShowBadge(false);
        notificationManager.createNotificationChannel(mChannel);
        Notification notification = new Notification.Builder(MainActivity.this, id).setContentTitle("Title")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentTitle("You have a new notification")
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentText("This is a message that makes you fun.")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(getString(R.string.dialog_message)))
                .setAutoCancel(false)
                .setContentIntent(pintent)
                .build();
        notificationManager.notify(1, notification);
    }

    private void sendNotification_N() {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("You have a new notification")
                .setContentText("This is a message that makes you fun.")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_LOW)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setLights(Color.RED, 1000, 1000)
                .build();
        notificationManager.notify(1, notification);
    }



    private String CHANNEL_ID = "CHANNEL_ID";

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotificationInbox(View view) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("5 New mails from 110")
                .setContentText("Show inbox mode")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("messageSnippet1")
                        .addLine("messageSnippet2"))
                .build();
        notificationManager.notify(5, notification);
    }


    public void sendNotificationMessages(View view) {

        NotificationCompat.MessagingStyle.Message message1 =
                new NotificationCompat.MessagingStyle.Message("messages0.Text",
                        System.currentTimeMillis(),
                        "messages0.Sender");
        NotificationCompat.MessagingStyle.Message message2 =
                new NotificationCompat.MessagingStyle.Message("messages1.Text",
                        System.currentTimeMillis() - 2000,
                        "messages1.Sender");

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.MessagingStyle("reply_name")
                        .addMessage(message1)
                        .addMessage(message2))
                .build();
        notificationManager.notify(6, notification);
    }

    public void sendNotificationCustomView(View view) {
        // Get the layouts to use in the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);

// Apply the layouts to the notification
        Notification customNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)

                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .build();
        notificationManager.notify(7, customNotification);
    }
}
