package com.example.regroup.Events;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.regroup.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.DatePickerDialog.*;

public class RegistrationEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME = "time";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IMAGE = "image";
    int day, month, year, hour, minute;
    int fday, fmonth, fyear, fhour, fminute;

    private static final String TAG = "EventRegistrationd";

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private TimePicker timePicker1;
    DatePickerDialog datePickerDialog;
    Calendar calendar;

    private EditText nameField;
    private EditText descriptionField;
    private EditText dateField;
    private EditText timeField;
    private EditText emailField;
    private EditText phoneField;
    private EditText addressField;
    private Button buttonAddEvent;
    private Button buttonPickDate;
    private Button buttonAddImage;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        nameField = (EditText) findViewById(R.id.ERName);
        descriptionField = (EditText) findViewById(R.id.ERdescription);
        dateField = (EditText) findViewById(R.id.ERdate);
        timeField = (EditText) findViewById(R.id.ERtime);
        emailField = (EditText) findViewById(R.id.ERmail);
        phoneField = (EditText) findViewById(R.id.ERphone);
        phoneField = (EditText) findViewById(R.id.ERphone);
        addressField = (EditText) findViewById(R.id.ERadress);
        buttonAddEvent = (Button) findViewById(R.id.ERaddEvent);
        buttonAddImage = (Button) findViewById(R.id.ERaddimage);
        buttonPickDate = (Button) findViewById(R.id.ERpickDate);
        image = (ImageView) findViewById(R.id.ERimage);

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date uDate;

    public void createEvent(View v) {
        String name = nameField.getText().toString();
        String description = descriptionField.getText().toString();
        String date = dateField.getText().toString();
        String time = timeField.getText().toString();
        String phone = phoneField.getText().toString();
        String email = emailField.getText().toString();
        String imageRef = UUID.randomUUID().toString();
        Map<String, Object> event = new HashMap<>();
        event.put(KEY_ID, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        event.put(KEY_NAME, name);
        event.put(KEY_DESCRIPTION, description);
        event.put(KEY_DATE, date);
        event.put(KEY_TIME, time);
        event.put(KEY_PHONE, phone);
        event.put(KEY_EMAIL, email);
        event.put(KEY_IMAGE, imageRef);

        final String id  = name+date;

        db.collection("events").document(id).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RegistrationEvent.this, "event saved", Toast.LENGTH_SHORT).show();

                /*Intent intent = new Intent(getApplicationContext(), EventPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/

                Intent intent = new Intent(getApplicationContext(), EventPage.class);
                Bundle b = new Bundle();
                b.putString("key", id); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationEvent.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });

        Log.i("IMAGE REF WHEN SETTING:", imageRef);
        uploadImage(imageRef);

    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
         }

    }
    private void uploadImage(String imageRef) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("Events_Images/"+ imageRef);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationEvent.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationEvent.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

   private void pickDate(){
       calendar = Calendar.getInstance();
       int day = calendar.get(Calendar.DAY_OF_MONTH);
       int month = calendar.get(Calendar.MONTH);
       int year = calendar.get(Calendar.YEAR);

       datePickerDialog = new DatePickerDialog(RegistrationEvent.this, RegistrationEvent.this,  day, month, year);
       datePickerDialog.show();
   }
    private void pickfDate(){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        hour = timePicker1.getCurrentHour();
        minute = timePicker1.getCurrentMinute();

        timeField.setText((hour+1)+"-"+(minute+1));

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        fyear = year;
        fmonth = month;
        fday = day;

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(RegistrationEvent.this, (TimePickerDialog.OnTimeSetListener) RegistrationEvent.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
   }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        fhour = hour;
        fmonth = minute;

        dateField.setText(fday+"-"+(fmonth)+"-"+fyear);
        timeField.setText(hour+":"+(minute));

    }
}
