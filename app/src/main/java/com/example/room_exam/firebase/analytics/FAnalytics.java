package com.example.room_exam.firebase.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FAnalytics {

    private FirebaseAnalytics mFirebaseAnalytics= null;



    public FAnalytics(){

    }

    public void initFirebaseAnalytics(Context context){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }


    public void sendLogEvent(String event_message ,String id, String name){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,  id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,  name);
        mFirebaseAnalytics.logEvent(event_message,bundle);
    }
}
