package com.votingapp;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.votingapp.entities.Voter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class CastVoteTest {

    @Rule
    public ActivityTestRule<CastVote> castVoteActivityTestRule = new ActivityTestRule<CastVote>(CastVote.class, false, false);
    private CastVote castVoteActivity = null;


    @Before
    public void setUp() throws Exception {
        String voterDetails = "001$Abhishek Mukherjee$Not Known$01/02/20$Kolkata$Male$321$123";
        String details[] = voterDetails.split("\\$");

        Voter voter = new Voter();
        voter.setVoter(details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7]);

        Intent i = new Intent();
        i.putExtra("CANDIDATES", "Arunava Chakraborty$1$Swapnil Mullick$3$Lalu$5$");
        i.putExtra("Voter", voter);
        castVoteActivityTestRule.launchActivity(i);
        castVoteActivity = castVoteActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {

        View v = castVoteActivity.findViewById(R.id.confirmVoteButton);
        assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
        castVoteActivity = null;
    }

}