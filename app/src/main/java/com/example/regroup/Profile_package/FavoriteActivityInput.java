package com.example.regroup.Profile_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.regroup.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class FavoriteActivityInput extends DialogFragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String uid;

    Button buttonAccept;
    Button buttonDismiss;

    CheckBox exerciseCB;
    CheckBox boardGamesCB;
    CheckBox videoGamesCB;
    CheckBox musicConcertsCB;

    String activities = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.favorite_activity_picker_dialog, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = currentFirebaseUser.getUid();

        setText(uid);

        buttonAccept = view.findViewById(R.id.accept);
        buttonDismiss = view.findViewById(R.id.dismiss);

        exerciseCB = view.findViewById(R.id.exerciseCB);
        boardGamesCB = view.findViewById(R.id.boardGamesCB);
        videoGamesCB = view.findViewById(R.id.videoGamesCB);
        musicConcertsCB = view.findViewById(R.id.musicConcertsCB);

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked();
                activitiesToDB(uid, activities);
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

    public void onCheckboxClicked(){
        activities = "";

        if (exerciseCB.isChecked())
            activities += "1 ";
        if (boardGamesCB.isChecked())
            activities += "2 ";
        if (videoGamesCB.isChecked())
            activities += "3 ";
        if (musicConcertsCB.isChecked())
            activities += "4 ";
    }

    public void activitiesToDB(String uid, String activities){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("Megstamos veiklos", activities);
    }

    public void setText(String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String activities = documentSnapshot.getString("Megstamos veiklos");
                    for (String i: activities.split(" ")) {
                        if(i.equals("1")){
                            exerciseCB.setChecked(true);
                        }
                        if(i.equals("2")){
                            boardGamesCB.setChecked(true);
                        }
                        if(i.equals("3")){
                            videoGamesCB.setChecked(true);
                        }
                        if(i.equals("4")){
                            musicConcertsCB.setChecked(true);
                        }
                    }
                }
            }
        });
    }
}
