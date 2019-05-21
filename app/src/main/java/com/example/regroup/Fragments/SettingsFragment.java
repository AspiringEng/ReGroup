package com.example.regroup.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.regroup.Events.EventManagementActivity;
import com.example.regroup.LoginScreen;
import com.example.regroup.MainActivity;
import com.example.regroup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button login = view.findViewById(R.id.loginba);
        login.setOnClickListener(this);

        Button del = view.findViewById(R.id.delete);
        del.setOnClickListener(this);

        Button eventMgmtBtn = view.findViewById(R.id.manageEventsBtn);

        eventMgmtBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getActivity(), EventManagementActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginba:
                openLogin();
                break;
            case  R.id.delete:
                delete();
                break;

        }
    }

    public void openLogin(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void delete(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();

        firebaseUser.delete();
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);
        getActivity().finish();

    }

}
