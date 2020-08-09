package com.solo003.shopmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    public static String phone;

    EditText mphone;
    Button generate;
    ProgressBar gbar;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mphone = findViewById(R.id.namefield);
        generate = findViewById(R.id.generatebtn);
        gbar = findViewById(R.id.gprogressBar);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mphone.getText().toString();

                if (phone.isEmpty()){
                    mphone.setError("Please enter your number");
                    mphone.requestFocus();
                }
                else if (phone.length() != 10){
                    mphone.setError("Please enter a valid number");
                    mphone.requestFocus();
                }
                else {
                    gbar.setVisibility(View.VISIBLE);
                    generate.setEnabled(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phone,
                            60,
                            TimeUnit.SECONDS,
                            Login.this,
                            mCallbacks
                    );
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(Login.this,"Sending verification code...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Login.this,"Please check your number", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Intent otpintent = new Intent(Login.this,Otp.class);
                otpintent.putExtra("AuthCredentials", s);
                startActivity(otpintent);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}