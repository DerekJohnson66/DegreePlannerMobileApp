package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String ASSESSMENT_CHANNEL_ID = "assessmentChannel";
    public static final String assessmentChannelName = "AssessmentChannel";
    public static final String COURSE_CHANNEL_ID = "assessmentChannel";
    public static final String courseChannelName = "CourseChannel";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createCourseChannel();
            createAssessmentChannel();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createCourseChannel(){
        NotificationChannel courseChannel = new NotificationChannel(COURSE_CHANNEL_ID, courseChannelName, NotificationManager.IMPORTANCE_DEFAULT);
        courseChannel.enableLights(true);
        courseChannel.enableVibration(true);
        courseChannel.setLightColor(R.color.colorPrimary);
        courseChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(courseChannel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createAssessmentChannel(){
        NotificationChannel assessmentChannel = new NotificationChannel(ASSESSMENT_CHANNEL_ID, assessmentChannelName, NotificationManager.IMPORTANCE_DEFAULT);
        assessmentChannel.enableLights(true);
        assessmentChannel.enableVibration(true);
        assessmentChannel.setLightColor(R.color.colorPrimary);
        assessmentChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(assessmentChannel);
    }

    public NotificationManager getManager(){
        if(mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getCourseChannelNotification (String title){
        return new NotificationCompat.Builder(getApplicationContext(), COURSE_CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_alert);
    }

    public NotificationCompat.Builder getAssessmentChannelNotification (String title){
        return new NotificationCompat.Builder(getApplicationContext(), ASSESSMENT_CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_alert);
    }


}
