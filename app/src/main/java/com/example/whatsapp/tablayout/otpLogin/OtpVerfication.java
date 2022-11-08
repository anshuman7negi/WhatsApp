package com.example.whatsapp.tablayout.otpLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.MainActivity;
import com.example.whatsapp.tablayout.profile.SelectProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerfication extends AppCompatActivity {

    private  String verificationId;
    EditText otpCode;
    Button verifyWhatsApp;
    FirebaseAuth mAuth;
    public static String login_Session="MyPrefName";
    SharedPreferences.Editor editor;
    ProgressBar otpVerificationProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verfication);

        otpCode=findViewById(R.id.otpCode);
        verifyWhatsApp=findViewById(R.id.verifyWhatsApp);
        verificationId=getIntent().getStringExtra("verificationId");
        otpVerificationProgress=findViewById(R.id.otpVerificationProgress);

        mAuth=FirebaseAuth.getInstance();

        verifyWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyWhatsApp.setVisibility(View.GONE);
                otpVerificationProgress.setVisibility(View.VISIBLE);
                verify();
            }
        });

    }

    private void verify()
    {
        String code=otpCode.getText().toString().trim();
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,code);

        signinWithCredential(credential);
    }

    private void signinWithCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpVerfication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {

                            otpVerificationProgress.setVisibility(View.GONE);
                            verifyWhatsApp.setVisibility(View.VISIBLE);

//                            SharedPreferences sharedPreferences=getSharedPreferences(OtpVerfication.login_Session,0);
//                             editor=sharedPreferences.edit();
//
//                             editor.putBoolean("hasLoggedIn",true);
//                             editor.commit();

                            Toast.makeText(OtpVerfication.this, "Login Sucess", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(OtpVerfication.this,SelectProfile.class));
                          finish();

                        }
                        else
                        {
                            otpVerificationProgress.setVisibility(View.GONE);
                            verifyWhatsApp.setVisibility(View.VISIBLE);
                            Toast.makeText(OtpVerfication.this, "OTP Not Verified", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OtpVerfication.this,OtpSend.class));
                            finish();
                        }

                    }
                });

    }

}