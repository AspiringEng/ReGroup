package com.example.regroup.Events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.regroup.MainActivity;
import com.example.regroup.R;
import com.example.regroup.Users.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
<<<<<<< HEAD
=======
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
>>>>>>> parent of d54a3f8... Revert "Chat fragment"

import java.sql.Date;
import java.sql.Time;

public class EventPage extends AppCompatActivity {

<<<<<<< HEAD
=======
    private static final String KEY_ID = "id";
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME = "time";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ORG = "organizer";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_CITY = "city";
    private static final String KEY_IMAGE = "image";

    private static final String TAG = "EventPage";

    final static Event[] event = new Event[1];

    FirebaseFirestore db = FirebaseFirestore.getInstance();
<<<<<<< HEAD

=======
    //FirebaseFirestore db = FirebaseFirestore.getInstance()
>>>>>>> parent of d54a3f8... Revert "Chat fragment"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




<<<<<<< HEAD

        DocumentReference evenRef = db.collection("events").document("event1");
=======
        Bundle b = getIntent().getExtras();
        String id = ""; // or other values
        if(b != null)
            id = b.getString("key");


        DocumentReference evenRef = db.collection("events").document(id);
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
        evenRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 event[0] = new Event(
<<<<<<< HEAD
                        documentSnapshot.getId(),
=======
                        documentSnapshot.getString(KEY_ID),
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
                        documentSnapshot.getString(KEY_NAME),
                        documentSnapshot.getString(KEY_DATE),
                        documentSnapshot.getString(KEY_ORG),
                        documentSnapshot.getString(KEY_TIME),
                        documentSnapshot.getString(KEY_ADDRESS),
                        documentSnapshot.getString(KEY_CITY),
                        documentSnapshot.getString(KEY_DESCRIPTION),
                        documentSnapshot.getString(KEY_PHONE),
                         documentSnapshot.getString(KEY_EMAIL),
                         documentSnapshot.getString(KEY_IMAGE)
                        );

            }
        })
<<<<<<< HEAD
=======

>>>>>>> parent of d54a3f8... Revert "Chat fragment"
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventPage.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
        //showEvent(event[0]);


<<<<<<< HEAD
=======

>>>>>>> parent of d54a3f8... Revert "Chat fragment"
        TextView nameField = findViewById(R.id.EName);
        TextView descriptionField = findViewById(R.id.Edescription);
        TextView dateField = findViewById(R.id.Edate);
        TextView timeField = findViewById(R.id.Etime);
        TextView emailField = findViewById(R.id.Email);
        TextView phoneField = findViewById(R.id.Ephone);
<<<<<<< HEAD
        TextView adressField = findViewById(R.id.Eadress);
=======
        TextView addressField = findViewById(R.id.Eadress);
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
        TextView city = findViewById(R.id.Ecity);
        ImageView image = findViewById(R.id.EImage);
        nameField.setText(event[0].getName());
    }

<<<<<<< HEAD
=======
    public void ShowEvent(String id){


    }



>>>>>>> parent of d54a3f8... Revert "Chat fragment"

}
