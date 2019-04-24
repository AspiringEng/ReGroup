package com.example.regroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button login = view.findViewById(R.id.loginba);
        Button del = view.findViewById(R.id.delete);
        login.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginba:
                openLogin();
                break;
            case R.id.delete:
                openDelete();
                break;
        }
    }

    public void openLogin(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);
    }

    public void openDelete(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser().delete();
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);
    }

}
