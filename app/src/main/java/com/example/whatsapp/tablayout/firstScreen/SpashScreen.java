package com.example.whatsapp.tablayout.firstScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.window.SplashScreen;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.MainActivity;
import com.example.whatsapp.tablayout.otpLogin.OtpSend;
import com.example.whatsapp.tablayout.otpLogin.OtpVerfication;
import com.example.whatsapp.tablayout.profile.SelectProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SpashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_spash_screen);

        getSupportActionBar().hide();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                if(firebaseUser!=null)
                {
                    Intent intent=new Intent(SpashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(SpashScreen.this, OtpSend.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);
    }
}