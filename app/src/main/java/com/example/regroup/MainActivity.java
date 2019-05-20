package com.example.regroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.regroup.Events.EventManagementActivity;
import com.example.regroup.Events.RegistrationEvent;
import com.example.regroup.Fragments.EventsFragment;
import com.example.regroup.Fragments.MatchesFragment;
import com.example.regroup.Fragments.SettingsFragment;
import com.example.regroup.Profile_package.MyProfileFragment;
import com.example.regroup.Profile_package.ShowProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.regroup.Chat.Fragments.*;

public class MainActivity extends AppCompatActivity {

    private String uid;

    private FirebaseAuth mAuth;

    private long backPressedTime;

    /*@Override
    protected void onStart() {
        super.onStart();


        //check if user is null
            Intent intent = new Intent(MainActivity.this, ShowProfileActivity.class);
            startActivity(intent);
            finish();
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_matches:
                                selectedFragment = new ChatsFragment();
                                
                            break;
                        case R.id.nav_events:
                            selectedFragment = new EventsFragment();
                            break;
                        case R.id.nav_myProfile:
                            Bundle bundle = new Bundle();
                            bundle.putString("uid", uid);
                            MyProfileFragment myProfileFragment = new MyProfileFragment();
                            myProfileFragment.setArguments(bundle);
                            selectedFragment = myProfileFragment;
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_settings);


        //createEvent(); Useless????

        /*Siek tiek pakeiciau, nes gan daznai uzluzdavo programa, kai nueidavau i
          My Profile skilti - Arturas */
        /*FirebaseUser currentFirebaseUser = mAuth.getInstance().getCurrentUser() ;
        uid = currentFirebaseUser.getUid();*/

        //uid = getIntent().getStringExtra("uid");

        //System.out.println("uid of user on mainActivity: " + uid);
    }

    // Uzblokuoja back mygtuka
    @Override
    public void onBackPressed()
    {
        if (backPressedTime + 500 > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }



   /* FirebaseDatabase database = FirebaseDatabase.getInstance();
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
*/
    public void startEventRegistration(View view) {
        Intent eventRegistration = new Intent(this, RegistrationEvent.class);
        startActivity(eventRegistration);

    }




}
