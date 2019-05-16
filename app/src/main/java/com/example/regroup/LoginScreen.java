package com.example.regroup;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CallbackManager mCallBackManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    String uid;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance(); // initializing FirebaseAuth instance
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        /*// Auto-login if user already signed in before.
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginScreen.this, MainActivity.class));
            finish();
        }*/

        // Setting up buttons and so on.
        Button loginButton = findViewById(R.id.button3);
        Button registerButton = findViewById(R.id.button2);
        TextView forgotPassword = findViewById(R.id.forgotpassword);

        // Initialize Facebook Login Button
        //FacebookSdk.sdkInitialize(getApplicationContext());
        mCallBackManager = CallbackManager.Factory.create();
        LoginButton loginButtonFB = findViewById(R.id.fbsignup);
        loginButtonFB.setReadPermissions("email", "public_profile");


        // Facebook login button listener.
        loginButtonFB.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                Toast.makeText(getApplicationContext(), "FB login successful", Toast.LENGTH_SHORT).show();

                getFacebookProfileData(loginResult.getAccessToken());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "facebook:onError", error);
            }
        });

        // Normal login button listener.
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
                                //Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Paema userio uid
                            uid = mAuth.getUid();

                            // Checks if user is in DB. If not, makes a new document about him.
                            db.collection("users").document(uid).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().exists()){
                                                Toast.makeText(getApplicationContext(), "User is in DB", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "User is not in DB", Toast.LENGTH_SHORT).show();
                                                //createUserInDB(uid);
                                            }
                                        }
                                    });

                            // Patikrina ar duomenys yra uzpildyti ar ne. Jei ne, meta i ProfileFilling activity. Jei taip, meta i MainActivity.
                            isProfileEmpty();
                        }
                    });
                }
            }
        });

        // Register Button listener.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, UserRegistration.class));
            }
        });

        // Forgot password TextView listener.
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, PasswordRecovery.class));
            }
        });
    }

    // Tikrina ar profilis yra uzpildytas.
    private void isProfileEmpty() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        Map<String, Object> user = document.getData();
                        String fName = user.get("Vardas").toString();
                        String lName = user.get("Pavarde").toString();
                        String date = user.get("Gimimo data").toString();
                        String city = user.get("Miestas").toString();
                        String bio = user.get("Bio").toString();
                        String activity = user.get("Megstamos veiklos").toString();

                        if (fName.isEmpty() || lName.isEmpty() || date.isEmpty() || city.isEmpty() || bio.isEmpty() || activity.isEmpty())
                        {
                            startActivity(new Intent(LoginScreen.this, ProfileFilling.class));
                        }
                        else
                        {
                            //Perduoda mainActivity uid, tam kad butu galima kitiems fragmentams
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Loggin successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginScreen.this, MainActivity.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Gets all the info from Facebook profile. [Only gets the first name and last name at the moment]
    private void getFacebookProfileData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    //Toast.makeText(getApplicationContext(), object.getString("name"), Toast.LENGTH_SHORT).show();
                    name = object.getString("name");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        request.executeAsync();
    }

    // Makes a new document is "user" collection which is named by uid.
    private void createUserInDB(String uid) {
        String[] firstAndLast = name.split(" ");

        Map<String, Object> user = new HashMap<>();
        user.put("Vardas", firstAndLast[0]);
        user.put("Pavarde", firstAndLast[1]);
        user.put("Miestas", "");
        user.put("Gimimo data", new Timestamp(new Date()));
        user.put("Bio", "");
        user.put("Megstamiausios veiklos", "");

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

    // Needed for facebook login to work.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Handles facebook access token and logs the user in.
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            uid = mAuth.getUid();

                            // Checks if user is in DB. If not, makes a new document about him.
                            db.collection("users").document(uid).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().exists()){
                                                Toast.makeText(getApplicationContext(), "User is in DB", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "User is not in DB", Toast.LENGTH_SHORT).show();
                                                createUserInDB(uid);
                                            }
                                        }
                                    });

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}