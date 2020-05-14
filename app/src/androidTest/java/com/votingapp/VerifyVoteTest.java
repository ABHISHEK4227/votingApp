package com.votingapp;


import android.content.Intent;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.votingapp.entities.Voter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class VerifyVoteTest {

    @Rule
    public ActivityTestRule<VerifyVote> verifyVoteActivityTestRule =new ActivityTestRule<VerifyVote>(VerifyVote.class,false,false);
    private VerifyVote verifyVoteActivity=null;


    @Before
    public void setUp() throws Exception {
        String voterDetails="001$Abhishek Mukherjee$Not Known$01/02/20$Kolkata$Male$321$123";
        String details[]=voterDetails.split("\\$");

        Voter voter = new Voter();
        voter.setVoter(details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7]);

        Intent i=new Intent();

        i.putExtra("Voter",voter);verifyVoteActivityTestRule.launchActivity(i);
        verifyVoteActivity=verifyVoteActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

        View v=verifyVoteActivity.findViewById(R.id.buttonUnknown);
        assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
        verifyVoteActivity=null;
    }

}