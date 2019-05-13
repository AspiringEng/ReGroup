package com.example.regroup.Profile_package;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.regroup.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NameInputDialog extends DialogFragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;
    private EditText nameET;
    private EditText lastNameET;
    private DatePicker birthDate;
    private Button accept;
    private Button dismiss;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.name_input_dialog, container, false);

        //Paima EditText
        nameET = view.findViewById(R.id.firstName);
        lastNameET = view.findViewById(R.id.lastName);

        //Paima DatePicker
        birthDate = view.findViewById(R.id.birthDate);

        //Paima Button
        accept = view.findViewById(R.id.accept);
        dismiss = view.findViewById(R.id.dismiss);

        //Paima uid prisijungusio vartotojo
        uid = getArguments().getString("uid");

        //Nustato anksciau parasyta varda ir pavarde, taip pat nustato aksciau pasirinkta gimimo data
        setText(uid);

        //Paspaudus mygtuka parasyti vardas ir pavarde, taip pat ir pasirinkta gimimo data yra issaugoma DB, Dialogas yra isjungiamas
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());
                Timestamp timestamp = new Timestamp(calendar.getTime());
                setName(uid, nameET.getText().toString(), lastNameET.getText().toString(), timestamp);
                dismiss();
            }
        });

        //Paspaudus mygtuka Dialogas yra isjungiamas
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    /**
     * vardas ir pavarde, taip pat ir pasirinkta gimimo data yra issaugoma DB
     * @param uid
     * @param vardas
     * @param pavarde
     * @param gimimoData
     */
    public void setName(String uid, String vardas, String pavarde, Timestamp gimimoData) {
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("Vardas", vardas);
        docRef.update("Pavarde", pavarde);
        docRef.update("Gimimo data", gimimoData);
    }

    /**
     * Nustato anksciau parasyta varda ir pavarde, taip pat nustato aksciau pasirinkta gimimo data
     * @param uid
     */
    public void setText(String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Date gimimoData = documentSnapshot.getDate("Gimimo data");
                    String vardas = documentSnapshot.getString("Vardas");
                    String pavarde = documentSnapshot.getString("Pavarde");
                    nameET.setText(vardas);
                    lastNameET.setText(pavarde);
                    birthDate.updateDate(gimimoData.getYear() + 1900, gimimoData.getMonth(), gimimoData.getDate());
                }
            }
        });
    }
}
