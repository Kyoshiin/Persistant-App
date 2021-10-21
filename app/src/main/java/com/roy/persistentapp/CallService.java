package com.roy.persistentapp;

import static com.roy.persistentapp.App.CHANNEL_ID;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;


public class CallService extends Service {
    private static final String TAG = "CallService";
    private AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long intervalMillis = 60 * 1000 * 24 * 60;//AlarmManager.INTERVAL_DAY;
        Log.d(TAG, "onStartCommand: before alarmManager Setting");
        Intent i = new Intent(this, CallReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 1, i, 0);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() + 1000, intervalMillis, pi);
        Log.d(TAG, "onStartCommand: after alarmManager Setting");


        /**
         * For showing foreground notification
         */
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
