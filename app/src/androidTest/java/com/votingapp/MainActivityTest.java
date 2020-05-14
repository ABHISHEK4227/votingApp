package com.votingapp;

import android.view.View;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity=null;

    @Before
    public void setUp() throws Exception {
        mainActivity=mainActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

        View v=mainActivity.findViewById(R.id.epic);
        assertNotNull(v);
    }

    @Test
    public void value1(){
        View v=mainActivity.findViewById(R.id.epic);
       final EditText p=(EditText)(mainActivity.findViewById(R.id.pass));
       final EditText e= (EditText)v;

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.setText("GDN0225185");

                p.setText("12345");
                assertEquals("GDN0225185",e.getText().toString());
            }
        });



    }


    @Test
    public void value2(){
        View v=mainActivity.findViewById(R.id.epic);
        final EditText p=(EditText)(mainActivity.findViewById(R.id.pass));
        final EditText e= (EditText)v;

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.setText("ROR9914721");

                p.setText("123");
                assertEquals("ROR9914721",e.getText().toString());
            }
        });




    }

    @After
    public void tearDown() throws Exception {
        mainActivity=null;
    }
}