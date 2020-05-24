package com.votingapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class FingerPrintStartTest {

    @Rule
    public ActivityTestRule<FingerPrintStart> fingerPrintStartActivityTestRule =new ActivityTestRule<FingerPrintStart>(FingerPrintStart.class,false,true);
    private FingerPrintStart fingerPrintStart=null;

    @Before
    public void setUp() throws Exception {

        fingerPrintStart=fingerPrintStartActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

       View v=fingerPrintStart.findViewById(R.id.fingerPrintImage);
       assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
    }
}