package com.example.regroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyProfileFragment extends Fragment implements NameEnterDialog.NameEnterDialogListener {

    TextView txtv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        txtv = view.findViewById(R.id.Name);

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment nameEnterDialog = new NameInputDialog();
                nameEnterDialog.show(getFragmentManager(), "nameEnterDialog");
            }
        });

        return view;
    }


    @Override
    public void applyName(String name) {
        txtv.setText(name);
    }
}
