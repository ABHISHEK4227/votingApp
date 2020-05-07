package com.votingapp;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.votingapp.entities.Voter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class VoterDetailsTest {

    @Rule
    public ActivityTestRule<VoterDetails>voterDetailsActivityTestRule =new ActivityTestRule<VoterDetails>(VoterDetails.class,false,false);
    private VoterDetails voterDetailsActivity=null;


    @Before
    public void setUp() throws Exception {
        String voterDetails="001$Abhishek Mukherjee$Not Known$01/02/20$Kolkata$Male$321$123";
        String details[]=voterDetails.split("\\$");

        Voter voter = new Voter();
        voter.setVoter(details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7]);

        Intent i=new Intent();

        i.putExtra("Voter",voter);voterDetailsActivityTestRule.launchActivity(i);
        voterDetailsActivity= voterDetailsActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

        View v=voterDetailsActivity.findViewById(R.id.voterDetailsConfirm);
        assertNotNull(v);
    }

    @Test
    public void value1(){
        final TextView e=(TextView) (voterDetailsActivity.findViewById(R.id.vd_name));
        voterDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {



                assertEquals("Abhishek Mukherjee",e.getText().toString());
            }
        });

    }


    @Test
    public void value2(){
        final TextView e=(TextView) (voterDetailsActivity.findViewById(R.id.vd_father_name));
        voterDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {



                assertEquals("Not Known",e.getText().toString());
            }
        });

    }


    @Test
    public void value3(){
        final TextView e=(TextView) (voterDetailsActivity.findViewById(R.id.vd_dob));
        voterDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {



                assertEquals("01/02/20",e.getText().toString());
            }
        });

    }


    @Test
    public void value4(){
        final TextView e=(TextView) (voterDetailsActivity.findViewById(R.id.vd_sex));
        voterDetailsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {



                assertEquals("Male",e.getText().toString());
            }
        });

    }



    @After
    public void tearDown() throws Exception {
        voterDetailsActivity=null;
    }

}