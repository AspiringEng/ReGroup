package com.example.regroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ProfileLocationInput extends DialogFragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String uid;

    Button buttonAccept;
    Button buttonDismiss;

    EditText cityET;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_location_input_dialog, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = currentFirebaseUser.getUid();

        buttonAccept = view.findViewById(R.id.accept);
        buttonDismiss = view.findViewById(R.id.dismiss);

        cityET = view.findViewById(R.id.profileCityET);

        getLocation(uid);

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation(uid, cityET.getText().toString());
                dismiss();
            }
        });

        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setLocation(String uid, String city) {
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("Miestas", city);
    }

    public void getLocation(String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String city = documentSnapshot.getString("Miestas");
                    cityET.setText(city);
                }
            }
        });
    }
}
