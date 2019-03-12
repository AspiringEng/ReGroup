package com.example.regroup;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class EventRegistration extends AppCompatActivity {


    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME = "time";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    private static final String TAG = "EventRegistrationdd";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText nameField;
    private EditText descriptionField;
    private EditText dateField;
    private EditText timeField;
    private EditText emailField;
    private EditText phoneField;
    // private Button buttonAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);


        nameField = (EditText) findViewById(R.id.eventName);
        descriptionField = (EditText) findViewById(R.id.eventDescription);
        dateField = (EditText) findViewById(R.id.eventDate);
        timeField = (EditText) findViewById(R.id.eventTime);
        emailField = (EditText) findViewById(R.id.eventEmail);
        phoneField = (EditText) findViewById(R.id.eventphone);
        //buttonAddEvent = (Button) findViewById(R.id.buttonAddEvent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date uDate;

    public void createEvent(View v) {
        String name = nameField.getText().toString();
        String description = descriptionField.getText().toString();
        String date = dateField.getText().toString();
        String time = timeField.getText().toString();
        String phone = phoneField.getText().toString();
        String email = emailField.getText().toString();

        Map<String, Object> event = new HashMap<>();
        event.put(KEY_NAME, name);
        event.put(KEY_DESCRIPTION, description);
        event.put(KEY_DATE, date);
        event.put(KEY_TIME, time);
        event.put(KEY_PHONE, phone);
        event.put(KEY_EMAIL, email);


        db.collection("events").document("event1").set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventRegistration.this, "event saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventRegistration.this, "ERROR", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
