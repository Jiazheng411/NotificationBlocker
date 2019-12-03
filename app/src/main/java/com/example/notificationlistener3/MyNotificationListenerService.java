package com.example.notificationlistener3;

import android.content.SharedPreferences;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

public class MyNotificationListenerService extends NotificationListenerService {
    SharedPreferences mSharedPreference;

    @Override
    public void onNotificationPosted(StatusBarNotification notification) {
        // get reference yo shared preferences
        mSharedPreference = getSharedPreferences("setting", MODE_PRIVATE);

        // get apps names that user wish to receive notification from
        String apps = mSharedPreference.getString(Util_String.APPS_RECEIVING_NOTIFICATION, "");
        Log.i("NotificationListener", "apps not being blocked: " + apps);
        HashSet<String> appsNotBlocking = new HashSet<>(Arrays.asList(apps.split(";")));

        // get whether app is in blocking mode
        boolean is_blocking = mSharedPreference.getBoolean(Util_String.IS_BLOCKING,false);
        Log.i("NotificationListener", "is in blocking mode: " + is_blocking);

        // app is in blocking mode
        if (is_blocking){
                // blocking notification
                if(!appsNotBlocking.contains(notification.getPackageName())){
                    cancelNotification(notification.getKey());
                    Log.i("NotificationListener", "application " + notification.getPackageName() + " is blocked");
            }
                // not blocking notification is user choose to receive notification from the app
                else{
                    Log.i("NotificationListener", "application" + notification.getPackageName() + "is not blocked");
                }
        }
        // app not in blocking mode
        else{
            Log.i("NotificationListener","Notification from " + notification.getPackageName() + "posted and not cancelled");
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("NotificationListener","Notification removed");
    }

}