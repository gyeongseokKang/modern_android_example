package com.example.room_exam.firebase.service;

import android.util.Log;
import android.widget.Switch;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final static String TAG = "FCM_MESSAGE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Notification Body: " );
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String clickAction = remoteMessage.getNotification().getClickAction();
            String form = remoteMessage.getFrom();
            String custom = remoteMessage.getData().get("custom");
            Log.d(TAG, "Notification Title: " + title);
            Log.d(TAG, "Notification Body: " + body);
            Log.d(TAG, "Notification clickation: " + clickAction);
            Log.d(TAG, "Notification form: " + form);
            Log.d(TAG, "Notification custom: " + custom);

            switch (custom){
                case "custom_test" : {
                    //do something!!!
                }
            }
        }
    }
}
