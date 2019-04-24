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
<<<<<<< HEAD
import android.widget.Switch;

public class SettingsFragment extends Fragment implements View.OnClickListener {

=======

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button logout;


>>>>>>> parent of d54a3f8... Revert "Chat fragment"
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
<<<<<<< HEAD
        Button login = view.findViewById(R.id.loginb);
=======
        Button login = view.findViewById(R.id.loginba);
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
        login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
<<<<<<< HEAD
            case R.id.loginb :
=======
            case R.id.loginba:
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
                openLogin();
                break;
        }
    }

    public void openLogin(){
<<<<<<< HEAD
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);

=======
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
    }
}
