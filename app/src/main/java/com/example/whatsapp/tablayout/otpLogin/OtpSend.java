package com.example.whatsapp.tablayout.otpLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpSend extends AppCompatActivity {

    EditText number;
    Button sendOtp;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_send);

        number=findViewById(R.id.number);
        sendOtp=findViewById(R.id.sendOtp);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.otpSendProgressBar);

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber=number.getText().toString();

                if(phoneNumber.trim().equals(""))
                {
                    number.setFocusable(true);
                    Toast.makeText(OtpSend.this, "Phone Number Missing", Toast.LENGTH_SHORT).show();
                }
                else if(phoneNumber.trim().length() !=10)
                {
                    number.setFocusable(true);
                    Toast.makeText(OtpSend.this, "Phone Number Invalid", Toast.LENGTH_SHORT).show();
                }

                else
                {
                   sendMsg();
                }


            }
        });

    }

    private  void sendMsg()
    {
        sendOtp.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                sendOtp.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(OtpSend.this, "failed:"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                sendOtp.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(OtpSend.this, "Otp send", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(OtpSend.this,OtpVerfication.class);
                intent.putExtra("phone",number.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


}