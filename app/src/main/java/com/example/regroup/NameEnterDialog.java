package com.example.regroup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

/**
 * Dialogas skirtas vardo ir pavardes ivedimui
 */
public class NameEnterDialog extends DialogFragment {
    private NameEnterDialogListener listener;
    private EditText nameET;
    private EditText lastNameET;
    private DatePicker birthDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.name_input_dialog, null);

        builder.setView(view);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String age = getAge(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());
                String name = nameET.getText().toString() + " " + lastNameET.getText().toString() + ", " + age;
                listener.applyName(name);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        nameET = view.findViewById(R.id.firstName);
        lastNameET = view.findViewById(R.id.lastName);
        birthDate = view.findViewById(R.id.birthDate);

        return builder.create();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NameEnterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement NameEnterDialogListener");
        }
    }

    public interface NameEnterDialogListener {
        public void applyName(String name);
    }

}
