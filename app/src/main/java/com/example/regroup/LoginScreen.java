package com.example.regroup;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getSupportActionBar().hide(); // Hides the top action bar
        mAuth = FirebaseAuth.getInstance(); // initializing FirebaseAuth instance

        Button loginButton = findViewById(R.id.button3);
        Button registerButton = findViewById(R.id.button2);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When Login button is clicked, do something...
                // Need to make it so when it's clicked it can't be clicked again until the credentials are checked and returned false

                String email = ((EditText)findViewById(R.id.editText2)).getText().toString();
                String password = ((EditText)findViewById(R.id.editText3)).getText().toString();

                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                startActivity(new Intent(LoginScreen.this, Profile.class));
                                //Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Loggin successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, UserRegistration.class));
            }
        });
    }
}