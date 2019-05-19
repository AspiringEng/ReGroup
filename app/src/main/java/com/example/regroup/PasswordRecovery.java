package com.example.regroup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordRecovery extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText email1;
    EditText email2;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        firebaseAuth = FirebaseAuth.getInstance();
        email1 = findViewById(R.id.email);
        email2 = findViewById(R.id.repeatemail);
        button = findViewById(R.id.sendbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if two emails match each other.
                // Check if email exists in DB.
                // Send email for changing password.

                String emailText1 = ((EditText)findViewById(R.id.email)).getText().toString();
                String emailText2 = ((EditText)findViewById(R.id.repeatemail)).getText().toString();

                if (emailText1.equals(emailText2))
                {
                    firebaseAuth.sendPasswordResetEmail(emailText1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Password recovery email has been sent!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {

                }
            }
        });
    }
}
