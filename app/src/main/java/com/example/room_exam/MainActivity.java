package com.example.room_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.room_exam.firebase.analytics.FAnalytics;
import com.example.room_exam.firebase.authentication.FirebaseUIActivity;
import com.example.room_exam.databinding.ActivityMainBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private FAnalytics mFAnalytics =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setLifecycleOwner(this);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);

        mFAnalytics = new FAnalytics();
        mFAnalytics.initFirebaseAnalytics(this);

        //firebase 인증
        binding.authBottom.setOnClickListener(v -> {
            mFAnalytics.sendLogEvent("click_test","id_test","name_test");
            Intent intent = new Intent(this, FirebaseUIActivity.class);
            startActivity(intent);
        });

        binding.addButton.setOnClickListener(v -> {
            mFAnalytics.sendLogEvent("click_test","id_test2","name_test2");
        });

        binding.btnRxandroid.setOnClickListener(v -> {
            Intent intent = new Intent(this, RxandroidActivity.class);
            startActivity(intent);
        });

        //getHashKey();
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


}
