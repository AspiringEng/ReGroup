package com.example.regroup.Profile_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.android.dx.command.Main;
import com.example.regroup.MainActivity;
import com.example.regroup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.app.Instrumentation;

import org.junit.Rule;
import org.junit.runner.RunWith;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MyProfileFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Mock
    MainActivity mMockMainActivity;

    @Mock
    private FirebaseFirestore dbMock;


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        dbMock = FirebaseFirestore.getInstance();
    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }

    @Test
    public void testLaunch() {
        FrameLayout rlContainer = mActivity.findViewById(R.id.fragment_container);
        assertNotNull(rlContainer);

        MyProfileFragment fragment = new MyProfileFragment();
        //mActivity.getFragmentManager().beginTransaction().add(rlContainer.getId(), new MyProfileFragment()).commitAllowingStateLoss();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlContainer.getId(), new MyProfileFragment()).commitAllowingStateLoss();
       // getInstrumentatuon()

       // Intent i = new Intent(MyProfileFragmentTest.this,MyProfileFragment.class);
        //startActivity(i);

        View view = fragment.getView().findViewById(R.id.nav_myProfile);
        assertNotNull(view);
    }

    @Test
    public void getName() {
        FrameLayout rlContainer = mActivity.findViewById(R.id.fragment_container);
        assertNotNull(rlContainer);

        MyProfileFragment fragment = new MyProfileFragment();
       // doNothing().when(fragment.getName()).then()

      //  when(fragment.getName("")).then(fragment.txtv.setText("Vardas Pavarde"));

/*
        FrameLayout rlContainer = mActivity.findViewById(R.id.fragment_container);
        MyProfileFragment fragment = new MyProfileFragment();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlContainer.getId(), new MyProfileFragment()).commitAllowingStateLoss();

        fragment.getName("6uyh2wKkUIdpsXdRZPCIKUiCkvB3");
        assertNotSame(fragment.txtv.getText(), null);*/
    }

    @Test
    public void getProfilePic() {
    }
}