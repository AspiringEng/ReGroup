package com.example.regroup.Users;

import android.support.v7.app.AppCompatDelegate;

import com.example.regroup.PasswordRecovery;
import com.example.regroup.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PasswordRecoveryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
        PasswordRecovery activity = spy(new PasswordRecovery());
        //doNothing().when(activity).initScreen();
        doNothing().when(activity).setContentView(R.layout.activity_password_recovery);
        doReturn(mock(AppCompatDelegate.class)).when(activity).getDelegate();

        // Call the method
        activity.onCreate(null);

        // Verify that it worked
        verify(activity, times(1)).setContentView(R.layout.activity_password_recovery);
      //  verify(activity, times(1)).initScreen();
    }
}