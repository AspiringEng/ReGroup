package com.example.regroup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.regroup.Chat.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.RegEx;

public class UserRegistration extends AppCompatActivity {


    DatabaseReference reference; // Real time DB needed for chat
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public void registerUser(final String Email, String password) {
        mAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "User has been registered!", Toast.LENGTH_SHORT).show();
                    String uid = mAuth.getUid();



                    //Reikia chat'ui
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    String temp = Email;
                    String regex_pattern = "@.+";
                    String username =  temp.replaceAll(regex_pattern,"");

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");
                    hashMap.put("search", username.toLowerCase());


                    //Arturo metodas

                    createUserInDB(uid);

                    //End of Arturo metodas

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(UserRegistration.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                    //End of chat stuff



                    // startActivity(new Intent(UserRegistration.this, MainActivity.class)); // Pakeistas i auksciau esanti koda (Veikimas nepasikeite)
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "User has not been registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUserInDB(String uid) {

        Map<String, Object> user = new HashMap<>();
        user.put("Vardas", "Vardas");
        user.put("Pavarde", "Pavarde");
        user.put("Miestas", "");
        user.put("Gimimo data", new Timestamp(new Date()));
        user.put("Bio", "");
        user.put("Megstamos veiklos", "");

        db.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


}
