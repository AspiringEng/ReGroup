package com.example.regroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class UserRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        getSupportActionBar().hide(); // Hides the top action bar
        mAuth = FirebaseAuth.getInstance(); // initializing FirebaseAuth instance
    }
}
