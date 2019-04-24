package com.example.regroup;

<<<<<<< HEAD
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
=======
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.jar.Attributes;

public class MyProfileFragment extends Fragment {

    TextView txtv;
    Button bioButton;
    String uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        txtv = view.findViewById(R.id.Name);
        bioButton = view.findViewById(R.id.bioButton);

        uid = getArguments().getString("uid");

        getName(uid);

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment nameInputDialog = new NameInputDialog();
                Bundle bundle = new Bundle();
                bundle.putString("uid", uid);
                nameInputDialog.setArguments(bundle);
                nameInputDialog.show(getFragmentManager(), "nameInputDialog");
            }
        });

        bioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment bioDialog = new BioDialog();
                Bundle bundle = new Bundle();
                bundle.putString("uid", uid);
                bioDialog.setArguments(bundle);
                bioDialog.show(getFragmentManager(), "bioDialog");
            }
        });

        return view;
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void getName(String uid){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Date gimimoData = documentSnapshot.getDate("Gimimo data");
                    System.out.println("Gimimo data: " + (gimimoData.getYear() + 1900) + " " + (gimimoData.getMonth() + 1) + " " + gimimoData.getDate());
                    String age = getAge((gimimoData.getYear() + 1900), (gimimoData.getMonth() + 1), gimimoData.getDate());
                    txtv.setText(documentSnapshot.getString("Vardas") + " " + documentSnapshot.getString("Pavarde") + ", " + age);
                }
            }
        });
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
    }
}
