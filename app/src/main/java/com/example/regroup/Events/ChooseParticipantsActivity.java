package com.example.regroup.Events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.regroup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseParticipantsActivity extends AppCompatActivity {

    String eventId;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = db.getReference().child("Users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_participants);

        eventId = getIntent().getStringExtra("eventId");

        usersRef.
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GetUsers((HashMap<String, Object>)dataSnapshot.getValue());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public class adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
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
            return null;
        }
    }

    public void GetUsers(HashMap<String, Object> users){
        for (Map.Entry<String, Object> entry: users.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            if(singleUser.get("connections")!=null){
                HashMap<String, Object> connection = (HashMap<String, Object>) singleUser.get("connections");
                HashMap<String, Object> yeps = (HashMap<String, Object>) connection.get("yep");
                for (Map.Entry<String, Object> yep: yeps.entrySet()) {
                    if(yep.getKey().equals(eventId)){
                        String userId = singleUser.get("id").toString();
                    }
                }


            }
        }
    }
}
