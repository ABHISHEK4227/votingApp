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

public class FacialRecognitionTest {

    @Rule
    public ActivityTestRule<FacialRecognition> facialRecognitionActivityTestRule =new ActivityTestRule<FacialRecognition>(FacialRecognition.class,false,false);
    private FacialRecognition facialRecognition=null;


    @Before
    public void setUp() throws Exception {
        String voterDetails="001$Abhishek Mukherjee$Not Known$01/02/20$Kolkata$Male$321$123";
        String details[]=voterDetails.split("\\$");

        Voter voter = new Voter();
        voter.setVoter(details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7]);

        Intent i=new Intent();
        i.putExtra("CANDIDATES","Arunava Chakraborty$1$Swapnil Mullick$3$Lalu$5$");
        i.putExtra("Voter",voter);facialRecognitionActivityTestRule.launchActivity(i);
        facialRecognition= facialRecognitionActivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){

        View v=facialRecognition.findViewById(R.id.faceRecCaptureButton);
        assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
        facialRecognition=null;
    }
}