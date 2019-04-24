package com.example.regroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.regroup.Events.Event;
import com.example.regroup.Events.EventPage;
import com.example.regroup.Events.RegistrationEvent;
import com.google.firebase.auth.FirebaseAuth;
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseUser;
=======
>>>>>>> parent of 389b82a... asd
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";  //gal...
<<<<<<< HEAD
    private String uid;
=======
>>>>>>> parent of 389b82a... asd

    private FirebaseAuth mAuth;

    //Sukuriu Bottom nav selection listeneri, tam, kad reaguotu i bottom nav paspaudimus
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_matches:
                            selectedFragment = new MatchesFragment();
                            break;
                        case R.id.nav_events:
                            selectedFragment = new EventsFragment();
                            break;
                        case R.id.nav_myProfile:
<<<<<<< HEAD
                            Bundle bundle = new Bundle();
                            bundle.putString("uid", uid);
                            MyProfileFragment myProfileFragment = new MyProfileFragment();
                            myProfileFragment.setArguments(bundle);
                            selectedFragment = myProfileFragment;
=======
                            selectedFragment = new MyProfileFragment();
>>>>>>> parent of 389b82a... asd
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    //Tam, kad rodytu fragmentus:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true; //Grazini true tam, kad rodytu kuri item'a pasirinkai
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //Pridedu listeneri, zino kuris nav pagal id (menu.bottom_nav.xml nurodyta)
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_events);
        Log.d("main", "The onCreate() event");
        createEvent();
<<<<<<< HEAD

        /*Siek tiek pakeiciau, nes gan daznai uzluzdavo programa, kai nueidavau i
          My Profile skilti - Arturas */
        FirebaseUser currentFirebaseUser = mAuth.getInstance().getCurrentUser() ;
        uid = currentFirebaseUser.getUid();

        //uid = getIntent().getStringExtra("uid");

        System.out.println("uid of user on mainActivity: " + uid);
=======
>>>>>>> parent of 389b82a... asd
    }



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date uDate;


    private void createEvent() {

        try {
            uDate = format.parse ( "2009-12-31" );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Event newEvent = new Event("3", "namfffe", new java.sql.Date(uDate.getTime()), null);

        myRef.child(newEvent.getEventId()).setValue(newEvent.toString());

        mAuth = FirebaseAuth.getInstance();
    }

    public void startEventRegistration(View view) {
        Intent eventRegistration = new Intent(this, RegistrationEvent.class);
        startActivity(eventRegistration);

    }




}
