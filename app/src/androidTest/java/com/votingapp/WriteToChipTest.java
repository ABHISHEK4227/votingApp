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

public class WriteToChipTest {

    @Rule
    public ActivityTestRule<WriteToChip> writeToChipActivityTestRule =new ActivityTestRule<WriteToChip>(WriteToChip.class,false,false);
    private WriteToChip writeToChipActivity=null;


    @Before
    public void setUp() throws Exception {
        String voterDetails="001$Abhishek Mukherjee$Not Known$01/02/20$Kolkata$Male$321$123";
        String details[]=voterDetails.split("\\$");

        Voter voter = new Voter();
        voter.setVoter(details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7]);

        Intent i=new Intent();
        i.putExtra("PARTYID",10);
        i.putExtra("Voter",voter);writeToChipActivityTestRule.launchActivity(i);
        writeToChipActivity= writeToChipActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

        View v=writeToChipActivity.findViewById(R.id.buttonExit);
        assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
        writeToChipActivity=null;
    }

}