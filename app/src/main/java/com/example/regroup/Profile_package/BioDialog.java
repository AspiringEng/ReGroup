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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BioDialog extends DialogFragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;
    Button accept;
    Button dismiss;
    private EditText bioET;

    /**
     * Inflatina dialoga ir nustato mygtuku veikima
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bio_dialog, container, false);

        //Paima uid prisijungusio vartotojo
        uid = getArguments().getString("uid");

        //Paima mygtukus
        accept = view.findViewById(R.id.acceptBio);
        dismiss = view.findViewById(R.id.dismissBio);
        bioET = view.findViewById(R.id.bioEditText);

        //Paima esama bio is DB
        setText(uid);

        //Jei paspaudziamas sis mygtukas, tai kas buvo irasyta i EditText yra ikeliama i duomenu baze ir Dialogas yra isjungiamas
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBio(bioET.getText().toString());
                dismiss();
            }
        });

        //Jei paspaudziamas sis mygtukas, niekas nera keiciama ir Dialogas yra isjungiamas
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    /**
     * Bio yra ikeliama i DB
     * @param bio
     */
    public void setBio(String bio) {

        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("Bio", bio);
    }

    /**
     * Nustato bio EditText teksta i ta kuris buvo paimtas is DB
     * @param uid
     */
    public void setText(String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String bio = documentSnapshot.getString("Bio");
                    bioET.setText(bio);
                }
            }
        });
    }
}
