package com.example.regroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getSupportActionBar().hide(); // Hides the top action bar
        mAuth = FirebaseAuth.getInstance(); // initializing FirebaseAuth instance

        Button btn = findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When Login button is clicked, do something...
                // Need to make it so when it's clicked it can't be clicked again until the credentials are checked and returned false

                Toast.makeText(getApplicationContext(), "veikia", Toast.LENGTH_SHORT).show(); // Just for testing
            }
        });
    }
}