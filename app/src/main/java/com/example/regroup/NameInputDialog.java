package com.example.regroup;

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

        nameET = view.findViewById(R.id.firstName);
        lastNameET = view.findViewById(R.id.lastName);
        birthDate = view.findViewById(R.id.birthDate);
        accept = view.findViewById(R.id.accept);
        dismiss = view.findViewById(R.id.dismiss);

        System.out.println("uid of user on NameInputDialog: " + getArguments().getString("uid"));
        uid = getArguments().getString("uid");

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "accept paspaude", Toast.LENGTH_SHORT).show();
                Calendar calendar = new GregorianCalendar(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());
                Timestamp timestamp = new Timestamp(calendar.getTime());
                setName(uid, nameET.getText().toString(), lastNameET.getText().toString(), timestamp);
                dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "dismiss paspaude", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    public void setName(String uid, String vardas, String pavarde, Timestamp gimimoData) {
        System.out.println("setName uid: " + uid + " vardas: " + vardas + " pavarde: " + pavarde + " gimimodata" + gimimoData);
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update("Vardas", vardas);
        docRef.update("Pavarde", pavarde);
        docRef.update("Gimimo data", gimimoData);
    }

}
