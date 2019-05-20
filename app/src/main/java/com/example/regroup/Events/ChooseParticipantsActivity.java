package com.example.regroup.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.regroup.Profile_package.ShowProfileActivity;
import com.example.regroup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseParticipantsActivity extends AppCompatActivity {

    String eventId;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = db.getReference().child("Users");

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference firestoreUsersRef = firestore.collection("users");

    ListView listView;
    Adapter adapter = new Adapter();
    ArrayList<String> userIds = new ArrayList<>();
    ArrayList<String> names;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_participants);

        eventId = getIntent().getStringExtra("eventId");

        names = new ArrayList<>();

        usersRef.
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GetUsers((HashMap<String, Object>)dataSnapshot.getValue());
                        firestoreUsersRef.get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (final DocumentSnapshot snapshot : task.getResult()) {
                                                if(userIds.contains(snapshot.get("id").toString())){
                                                    names.add(snapshot.get("Vardas").toString());
                                                }
                                            }
                                        }
                                        listView = findViewById(R.id.participantsListView);
                                        listView.setAdapter(adapter);

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


    }

    public class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.custom_chooseparticipants_layout, null);

            final int xd = position;

            TextView name = convertView.findViewById(R.id.nameOfParticipant);
            AppCompatImageButton profile = convertView.findViewById(R.id.participants_profile);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openShowProfileActivity(userIds.get(xd));
                }
            });

            name.setText(names.toArray()[position].toString());
            Log.i("TEST", "getView: " + names.toArray()[position].toString());

            return convertView;
        }
    }

    public void GetUsers(HashMap<String, Object> users){
        for (Map.Entry<String, Object> entry: users.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            if(singleUser.get("connections")!=null){
                HashMap<String, Object> connection = (HashMap<String, Object>) singleUser.get("connections");
                HashMap<String, Object> yeps = (HashMap<String, Object>) connection.get("yep");
                if(yeps != null){
                    for (Map.Entry<String, Object> yep: yeps.entrySet()) {
                        if(yep.getKey().equals(eventId)){
                            String userId = singleUser.get("id").toString();
                            userIds.add(userId);
                            Log.i("TEST", "Id added: " + userId);
                        }
                    }
                }
            }
        }
    }

    public void openShowProfileActivity(String userId){
        Intent intent = new Intent(this, ShowProfileActivity.class);
        intent.putExtra("userId", userId);

        startActivity(intent);
    }
}
