package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VerifyVote extends AppCompatActivity {
    private Voter voter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadActivity();
    }

    void loadActivity(){
        setContentView(R.layout.activity_verify_vote);

        Intent g = getIntent();
        voter = (Voter) g.getSerializableExtra("Voter");

        ((TextView)findViewById(R.id.vv_epic)).setText(voter.getName());
    }
}
