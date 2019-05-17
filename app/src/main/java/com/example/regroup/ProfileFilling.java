package com.example.regroup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class ProfileFilling extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateText;
    private Button joinbutton;
    private EditText fnamefield;
    private EditText lnamefield;
    private EditText cityfield;
    private EditText biofield;
    private EditText activityfield;
    private int Year, Month, Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_filling);

        dateText = findViewById(R.id.birthSelect);
        joinbutton = findViewById(R.id.joinButton);
        fnamefield = findViewById(R.id.firstName);
        lnamefield = findViewById(R.id.lastName);
        cityfield = findViewById(R.id.cityInput);
        biofield = findViewById(R.id.bioInput);
        activityfield = findViewById(R.id.activityInput);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        Map<String, Object> user = document.getData();
                        fnamefield.setText(user.get("Vardas").toString());
                        lnamefield.setText(user.get("Pavarde").toString());
                        Timestamp time = (Timestamp) user.get("Gimimo data");
                        Date date = time.toDate();
                        Year = date.getYear() + 1900;
                        Month = date.getMonth();
                        Day = date.getDay();
                        dateText.setText(Year + "-" + Month + "-" + Day);
                        cityfield.setText(user.get("Miestas").toString());
                        biofield.setText(user.get("Bio").toString());
                        activityfield.setText(user.get("Megstamos veiklos").toString());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // When TextView is pressed, a calendar pops up.
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // When button is pressed, all the information about the user is added to the DB.
        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = ((EditText)findViewById(R.id.firstName)).getText().toString();
                String lName = ((EditText)findViewById(R.id.lastName)).getText().toString();
                String date = ((TextView)findViewById(R.id.birthSelect)).getText().toString();
                String city = ((EditText)findViewById(R.id.cityInput)).getText().toString();
                String bio = ((EditText)findViewById(R.id.bioInput)).getText().toString();
                String activity = ((EditText)findViewById(R.id.activityInput)).getText().toString();

                Calendar calendar = new GregorianCalendar(Year, Month, Day);
                Timestamp time = new Timestamp(calendar.getTime());

                if (fName.isEmpty() || lName.isEmpty() || date.isEmpty() || city.isEmpty() || bio.isEmpty() || activity.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You must enter in everything first!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    DocumentReference Ref = db.collection("users").document(currentUser.getUid());

                    Ref.update(
                            "Vardas", fName,
                            "Pavarde", lName,
                            "Gimimo data", time,
                            "Miestas", city,
                            "Bio", bio,
                            "Megstamos veiklos", activity
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Welcome to ReGroup!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileFilling.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "An error has occurred!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    // When date is set, it saves the information.
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
        dateText.setText(date);
        Year = year;
        Month = month + 1;
        Day = dayOfMonth;
    }

    // Shows the DatePicker Dialog
    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                AlertDialog.THEME_HOLO_LIGHT,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
