package com.example.regroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText e1;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        dialog = new ProgressDialog(this);
        e1 = (EditText)findViewById(R.id.editText4);
        auth = FirebaseAuth.getInstance();


        final Button changeButton = findViewById(R.id.button4);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When Login button is clicked, do something...
                // Need to make it so when it's clicked it can't be clicked again until the credentials are checked and returned false

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if(user!= null){
            dialog.setMessage("Changing password, please wait!");
            dialog.show();
            FirebaseUser user1 = auth.getCurrentUser();
            user1.updatePassword(e1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Your password has been changed", Toast.LENGTH_LONG).show();
                        auth.signOut();
                        finish();
                        Intent i = new Intent(ChangePassword.this, LoginScreen.class);
                        startActivity(i);
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Password could not be changed", Toast.LENGTH_LONG).show();

                    }
                }
            });

            }
            }});

    }
}
