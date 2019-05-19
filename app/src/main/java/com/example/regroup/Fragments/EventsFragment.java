package com.example.regroup.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.regroup.Events.MyArrayAdapter;
import com.example.regroup.Events.cards;
import com.example.regroup.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference eventsRef = db.collection("events");

    DatabaseReference userRef;

    private FirebaseAuth mAuth;
    private String currentUId;

    List<cards> rowItems;

    private MyArrayAdapter arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUId);


        Log.i("USER", userRef.toString());
        rowItems = new ArrayList<>();

        arrayAdapter = new MyArrayAdapter(getActivity(), R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        eventsRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.i("DATABASE ACCESSED", "SUCCESSFULLY: ");
                        if(!queryDocumentSnapshots.isEmpty()){
                            for (final DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                userRef.child("connections").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.child("yep").hasChild(snapshot.toObject(cards.class).getId()) && !dataSnapshot.child("nope").hasChild(snapshot.toObject(cards.class).getId())){
                                            rowItems.add(snapshot.toObject(cards.class));
                                            arrayAdapter.notifyDataSetChanged();
                                            Log.i("INIF", snapshot.toObject(cards.class).getId().toString());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getActivity(), "Something went wrong when getting events", Toast.LENGTH_SHORT);
                                    }
                                });
                                arrayAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "No events to show", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Couldn't load the collection", Toast.LENGTH_LONG).show();
                    }
                });

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                //al.remove(0);
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("Id", currentUId);
//                cards obj = (cards) dataObject;
//                String eventId = obj.getId();
//                String eventName = obj.getName();
//                String eventDate = obj.getDate();
//                Log.i("ON EXIT INFO", eventId + " ---- " + eventName);
//                eventsRef.document(eventName + eventDate).collection("Nope").add(map);

                  cards obj = (cards) dataObject;
                  String eventId = obj.getId();
                  userRef.child("connections").child("nope").child(eventId).setValue(true);

                Toast.makeText(getActivity(), "Nope!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("Id", currentUId);
//                cards obj = (cards) dataObject;
//                String eventId = obj.getId();
//                String eventName = obj.getName();
//                String eventDate = obj.getDate();
//                Log.i("ON EXIT INFO", eventId + " ---- " + eventName);
//                eventsRef.document(eventName + eventDate).collection("Yep").add(map);

                cards obj = (cards) dataObject;
                String eventId = obj.getId();
                userRef.child("connections").child("yep").child(eventId).setValue(true);

                Toast.makeText(getActivity(), "Yep!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
}
