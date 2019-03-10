package com.example.regroup.Events;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.regroup.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class EventPage extends AppCompatActivity {

    private static final String TAG = "EventPage";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIME = "time";
    private static final String KEY_CITY = "city";
    private static final String KEY_IMAGE = "image";

    private EditText eventName;
    private EditText eventTime;
    private EditText eventCity;
    private ImageView eventImage;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference eventRef;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);

        eventName = findViewById(R.id.eventName);
        eventTime = findViewById(R.id.eventTime);
        eventCity = findViewById(R.id.eventCity);
        eventImage = findViewById(R.id.eventImageView);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void loadEvent(View v) {
        eventRef = db.collection("events").document("event");

        eventRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            eventName.setText(documentSnapshot.getString(KEY_NAME));
                            eventTime.setText(documentSnapshot.getString(KEY_TIME));
                            eventCity.setText(documentSnapshot.getString(KEY_CITY));
                        } else{
                            Toast.makeText( EventPage.this, "Event does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventPage.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });



    }
}
