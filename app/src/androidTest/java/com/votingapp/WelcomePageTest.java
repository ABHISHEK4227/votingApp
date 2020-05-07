package com.votingapp;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class WelcomePageTest {



    @Rule
    public ActivityTestRule<WelcomePage> welcomePageActivityTestRule =new ActivityTestRule<WelcomePage>(WelcomePage.class,false,false);
    private WelcomePage welcomePageActivity=null;


    @Before
    public void setUp() throws Exception {
        Intent i=new Intent();

        i.putExtra("EPIC", "001");
        i.putExtra("PASS", "123");
        i.putExtra("DETAILS", "001$Abhishek Mukherjee$Not Known$01/02/20$Kolkata$Male$321$123");
        welcomePageActivityTestRule.launchActivity(i);
        welcomePageActivity=welcomePageActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

        View v=welcomePageActivity.findViewById(R.id.buttonVerifyVote);
        assertNotNull(v);
    }

    @Test
    public void value1(){
        final TextView e=(TextView) (welcomePageActivity.findViewById(R.id.epicno));
        welcomePageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {



                assertEquals("001",e.getText().toString());
            }
        });

    }

    @Test
    public void value2(){
        final TextView e=(TextView) (welcomePageActivity.findViewById(R.id.name));
        welcomePageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {



                assertEquals("Abhishek Mukherjee",e.getText().toString());
            }
        });

    }

    @After
    public void tearDown() throws Exception {
        welcomePageActivity=null;
    }

}