package com.example.regroup.Profile_package;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regroup.MainActivity;
import com.example.regroup.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class ShowProfileActivity extends AppCompatActivity {

     ImageView IMG;
     TextView NAME;
     TextView BIO;
     TextView LOCATION;
     TextView ACTIVITIES;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://regroup-7c1c2.appspot.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        NAME = findViewById(R.id.Name);
        BIO = findViewById(R.id.bioText);
        LOCATION = findViewById(R.id.locationText);
        ACTIVITIES = findViewById(R.id.activitiesText);
        IMG = findViewById(R.id.profileIMG);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference usersRef = db.collection("users");

        //DocumentReference docRef = db.collection("users").document(currentUser.getUid());

        usersRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (final DocumentSnapshot snapshot: task.getResult()) {
                                if(snapshot.getString("Vardas").equals("Keksas"))
                                {
                                    setProfilePic(snapshot.getString("id"));
                                    NAME.setText(snapshot.getString("Vardas") + " " + snapshot.getString("Pavarde"));
                                    BIO.setText(snapshot.getString("Bio"));
                                    LOCATION.setText(snapshot.getString("Miestas"));
                                    ACTIVITIES.setText(snapshot.getString("Megstamos veiklos"));
                                }
                            }
                        }
                    }
                });


         }

                    public void setProfilePic(String uid){
                        final StorageReference imagesRef = storageRef.child("Users_Images/" + uid + ".jpeg");
                        final long ONE_MEGABYTE = 1024 * 1024;
                        imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                System.out.println("getProfilePic: success");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                IMG.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("getProfilePic: failure exception: " + e.toString() + " location: " + storageRef.getName());
                            }
                        });
                    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ShowProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

