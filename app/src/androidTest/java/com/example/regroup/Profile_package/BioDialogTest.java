package com.example.regroup.Profile_package;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.FrameLayout;

import com.example.regroup.MainActivity;
import com.example.regroup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BioDialogTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    //
    private DatabaseReference mockedDatabaseReference;


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseFirestore mockedFirebaseDatabase = Mockito.mock(FirebaseFirestore.class);
       // when(mockedFirebaseDatabase.collection("users").document()).thenReturn(mockedDatabaseReference);

        //PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(mockedFirebaseDatabase);

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

    @Test
    public void onCreateView() {
        FrameLayout rlContainer = mActivity.findViewById(R.id.fragment_container);
        assertNotNull(rlContainer);

        BioDialog fragment = new BioDialog();
        //mActivity.getFragmentManager().beginTransaction().add(rlContainer.getId(), new MyProfileFragment()).commitAllowingStateLoss();
        mActivity.getSupportFragmentManager().beginTransaction().add(rlContainer.getId(), new BioDialog()).commitAllowingStateLoss();
        // getInstrumentatuon()
        View view = fragment.getView().findViewById(R.id.scrollView2);
        assertNotNull(view);
    }

    @Test
    public void setBio() {


    }

    @Test
    public void setText() {

    }
}