package com.example.regroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.regroup.Events.Event;
import com.example.regroup.Events.EventPage;
import com.example.regroup.Events.RegistrationEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";  //gal...

    private FirebaseAuth mAuth;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("main", "The onCreate() event");
        createEvent();
    }


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date uDate;


    private void createEvent() {

        try {
            uDate = format.parse ( "2009-12-31" );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Event newEvent = new Event("3", "namfffe", new java.sql.Date(uDate.getTime()), null);

        myRef.child(newEvent.getEventId()).setValue(newEvent.toString());

        mAuth = FirebaseAuth.getInstance();
    }

    public void startEventRegistration(View view) {
        Intent eventRegistration = new Intent(this, RegistrationEvent.class);
        startActivity(eventRegistration);

    }




}
