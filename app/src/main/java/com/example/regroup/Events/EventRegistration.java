package com.example.regroup.Events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.regroup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class EventRegistration extends AppCompatActivity {


    private static final String TAG = "MainActivity";  //gal...
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_registration);

        final EditText name = (EditText) findViewById(R.id.eventName);
        final EditText description = (EditText) findViewById(R.id.eventDescription);
        final EditText date = (EditText) findViewById(R.id.eventDate);
        final EditText time = (EditText) findViewById(R.id.eventTime);
        final EditText email = (EditText) findViewById(R.id.eventEmail);
        final EditText phone = (EditText) findViewById(R.id.eventphone);
        final Button buttonAddEvent = (Button) findViewById(R.id.buttonAddEvent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date uDate;

    public void seeWholeEvent(){







        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        Event newEvent = new Event("2", "vardas", new java.sql.Date(uDate.getTime()), null);

        myRef.child(newEvent.getEventId()).setValue(newEvent.toString());

        myRef.setValue("Hello, World!");
    }



}
