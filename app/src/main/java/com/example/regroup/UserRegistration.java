package com.example.regroup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class UserRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration2);

        mAuth = FirebaseAuth.getInstance(); // initializing FirebaseAuth instance

        Button registerButton = findViewById(R.id.userRegButton);
        final CheckBox checkboxTicked = findViewById(R.id.checkBox);

        // When registration button is clicked, do registration tasks.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.emailText1)).getText().toString();
                String password1 = ((EditText)findViewById(R.id.passwordText1)).getText().toString();
                String password2 = ((EditText)findViewById(R.id.passwordText2)).getText().toString();

                // Check if checkBox is checked.
                // Check if two passwords are equal.
                // check if password is long enough. [min 7 characters]
                // Check if the entered email exists or not
                if (checkboxTicked.isChecked())
                {
                    if (password1.equals(password2))
                    {
                        if (password1.length() >= 7)
                        {
                            checkEmail(email, password1);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Password is not long enough. Min 7 characters!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Passwords are not matching.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You didn't agree to Terms of Use.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkEmail(final String email, final String password) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = task.getResult().getSignInMethods().isEmpty();

                if (check)
                {
                    //Toast.makeText(getApplicationContext(), "user does not exist", Toast.LENGTH_SHORT).show();
                    registerUser(email, password);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "This email is already used.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "User has been registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserRegistration.this, Profile.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "User has not been registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
