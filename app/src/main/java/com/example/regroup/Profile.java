package com.example.regroup;

<<<<<<< HEAD
=======
import android.graphics.Bitmap;
>>>>>>> parent of d54a3f8... Revert "Chat fragment"
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Vytenis
 * Profilio lango klase
 */
public class Profile extends AppCompatActivity implements NameEnterDialog.NameEnterDialogListener {
    TextView txtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //komentaras
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        txtv = findViewById(R.id.Name);

<<<<<<< HEAD
=======

>>>>>>> parent of d54a3f8... Revert "Chat fragment"
        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment nameEnterDialog = new NameEnterDialog();
                nameEnterDialog.show(getSupportFragmentManager(), "nameEnterDialog");
            }
        });
    }

    @Override
    public void applyName(String name) {
        txtv.setText(name);
    }
}
