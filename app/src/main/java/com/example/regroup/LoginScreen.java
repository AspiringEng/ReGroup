package com.example.regroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getSupportActionBar().hide(); // Hides the top action bar
    }
}
