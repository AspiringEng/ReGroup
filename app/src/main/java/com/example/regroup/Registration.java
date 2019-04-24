package com.example.regroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.regroup.Events.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Registration  extends AppCompatActivity implements View.OnClickListener {

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("registration", "The onStart() event");
    }

    private FirebaseAuth mAuth;
    private static final String TAG = "Registration";  //gal...
    private Button button;
    private TextView textView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
<<<<<<< HEAD
        button = (Button) findViewById(R.id.button);
=======
        button = (Button) findViewById(R.id.deleteProf);
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
        textView = (TextView) findViewById(R.id.editText);

        button.setOnClickListener(this);
    }

    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date uDate;

    @Override
    public void onClick(View v) {
        buttonClicked();

    }

    private void buttonClicked() {
        button.setText("Clicked");

        try {
            uDate = format.parse ( "2009-12-31" );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String name = textView.getText().toString();

        Event newEvent = new Event("2", name, new java.sql.Date(uDate.getTime()), null);

        myRef.child(newEvent.getEventId()).setValue(newEvent.toString());

        mAuth = FirebaseAuth.getInstance();
    }


    }

