package com.example.lakasdekorshop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "LakásDekorNotificationChannel";

    Context context;
    NotificationManager manager;

    public  NotificationHandler(Context context){
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "LakásDekorNotification",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.enableVibration(true);
        channel.setDescription("Notifications from LakásDekor");
        this.manager.createNotificationChannel(channel);
    }

    public void send(String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("LakásDekor")
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_shopping_cart_24);

        this.manager.notify(0, builder.build());
    }
}
