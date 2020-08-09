package com.solo003.shopmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Dtladd extends AppCompatActivity {

    EditText mname;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtladd);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mname = findViewById(R.id.namefield);

        findViewById(R.id.continuebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mname.getText().toString();

                userid = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("Faculity").document(userid);
                Map<String,Object> user = new HashMap<>();
                user.put("Name",name);
                user.put("Phone No",Login.phone);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Dtladd.this, "Details Added", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}