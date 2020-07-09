package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        String title = "";
        NotificationCompat.Builder nb = notificationHelper.getCourseChannelNotification(title);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
