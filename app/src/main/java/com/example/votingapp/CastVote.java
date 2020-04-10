package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

public class CastVote extends AppCompatActivity {
    private Button confirmButton;
    private ScrollView scrollV;
    private RadioGroup radioG;
    // fetch Candidate List from voteManager
    private CandidateList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        confirmButton = (Button) findViewById(R.id.confirmVote);
        scrollV = (ScrollView) findViewById(R.id.scrollview_vote);
        radioG = (RadioGroup) findViewById(R.id.radiogroup_vote);

//        list = fetchCandidateList();
        Candidate c0 = new Candidate(new String("Abhishek"), 0);
        Candidate c1 = new Candidate(new String("Ranajit"), 1);
        Candidate c2 = new Candidate(new String("Arnab"), 2);
        list = new CandidateList(c0);
        list.push(c1);
        list.push(c2);

        populate();
    }

    public void populate() {
        LinearLayout loading = (LinearLayout) findViewById(R.id.vote_loading);
        radioG.removeView(loading);
    }

}
