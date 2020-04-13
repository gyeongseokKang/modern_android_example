package com.example.room_exam.firebase.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.room_exam.R;
import com.example.room_exam.databinding.ActivityFirebaseUiBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class FirebaseUIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGH_IN = 1000;
    private  ActivityFirebaseUiBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firebase_ui);
        binding.firebaseUiAuthBtn.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGH_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(requestCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Intent intent = new Intent(this, SignedInActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Auth failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.firebase_ui_auth_btn:
                    signin();
                    break;
                default:
                    break;
            }
    }

    /**
     * 인증요청
     */
    private void signin(){
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(getSelectedTheme())
                .setLogo(getSelectedLogo())
                .setAvailableProviders(getSelectedProviders())
                .setTosAndPrivacyPolicyUrls("https://naver.com","https://google.com")
                .setIsSmartLockEnabled(true)
                .build(),RC_SIGH_IN
        );
    }

    /**
     * FireBaseUI return 테이터
     */
    private int getSelectedTheme(){
        return AuthUI.getDefaultTheme();
    }
    private int getSelectedLogo(){
        return AuthUI.NO_LOGO;
    }
    private List<AuthUI.IdpConfig> getSelectedProviders(){
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        if(binding.checkBoxFacebook.isChecked()){
            selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        }

        if(binding.checkBoxGoogle.isChecked()){
            selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        }

        return selectedProviders;
    }
}
