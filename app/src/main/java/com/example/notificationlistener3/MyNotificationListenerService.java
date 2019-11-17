package com.example.notificationlistener3;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class MyNotificationListenerService extends NotificationListenerService {
    SharedPreferences mSharedPreference;
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        mSharedPreference = getSharedPreferences("setting", MODE_PRIVATE);
        boolean contains = mSharedPreference.contains(Util_String.IS_BLOCKING);
        Log.i("notification service", "sharedpreference contains key Is blocking" + contains);
        boolean is_blocking = mSharedPreference.getBoolean(Util_String.IS_BLOCKING,false);
        Log.i("NotificationListener", "" + is_blocking);
        if (is_blocking){
            cancelNotification(sbn.getKey());
            Log.i("NotificationListener","Notification posted and cancelled");
        }
        else{
            Log.i("NotificationListener","Notification posted and not cancelled");
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("NotificationListener","Notification removed");
    }



}