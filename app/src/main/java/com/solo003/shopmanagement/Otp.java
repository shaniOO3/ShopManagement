package com.solo003.shopmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Otp extends AppCompatActivity {

    Button verify;
    EditText motp;
    ProgressBar vbar;
    FirebaseAuth mAuth;
    String mAuthVerificationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        mAuthVerificationid = getIntent().getStringExtra("AuthCredentials");
        motp = findViewById(R.id.namefield);
        vbar = findViewById(R.id.vprogressBar);
        verify = findViewById(R.id.verifybtn);

        findViewById(R.id.verifybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = motp.getText().toString();

                if (otp.isEmpty()){
                    motp.setError("Please enter the OTP");
                    motp.requestFocus();
                }
                else {
                    vbar.setVisibility(View.VISIBLE);
                    verify.setEnabled(false);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationid, otp);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(getApplicationContext(),Dtladd.class));
                            finish();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                motp.setError("Please enter a valid OTP");
                                motp.requestFocus();
                            }
                        }
                        vbar.setVisibility(View.INVISIBLE);
                        verify.setEnabled(true);
                    }
                });
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