package com.example.regroup.Profile_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.regroup.R;
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

        //Paima uid prisijungusio vartotojo
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = currentFirebaseUser.getUid();

        //Paima Button
        buttonAccept = view.findViewById(R.id.accept);
        buttonDismiss = view.findViewById(R.id.dismiss);

        //Paima EditText
        cityET = view.findViewById(R.id.profileCityET);

        //Paima anksciau parasyta miesta
        getLocation(uid);

        //Paspaudus mygtuka parasytas miestas yra issaugojamas i DB, Dialogas yra isjungiamas
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation(uid, cityET.getText().toString());
                dismiss();
            }
        });

        //Paspaudus mygtuka Dialogas yra isjungiamas
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    /**
     * miestas yra issaugojamas i DB
     * @param uid
     * @param city
     */
    public void setLocation(String uid, String city) {
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("Miestas", city);
    }

    /**
     * Paima anksciau parasyta miesta
     * @param uid
     */
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
