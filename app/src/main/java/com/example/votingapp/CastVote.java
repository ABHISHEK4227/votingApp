package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class CastVote extends AppCompatActivity {
    private Button confirmButton;
    private RadioGroup radioG;
    // fetch Candidate List from voteManager
    private CandidateList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        Intent g=getIntent();
        String Candidate_deails=g.getStringExtra("CANDIDATES");
        list = null;
        confirmButton = (Button) findViewById(R.id.confirmVoteButton);
        radioG = (RadioGroup) findViewById(R.id.radiogroup_vote);

//      fetch Candidates from
        Candidate c0 = new Candidate(new String("Abhishek"), 0);
        Candidate c1 = new Candidate(new String("Ranajit"), 1);
        Candidate c2 = new Candidate(new String("Swapnil"), 2);
        Candidate c3 = new Candidate(new String("Ankur"), 3);
        Candidate c4 = new Candidate(new String("Arnab"), 4);
        Candidate c5 = new Candidate(new String("Arunava"), 5);
        list = new CandidateList(c0);
        list.push(c1);
        list.push(c2);
        list.push(c3);
        list.push(c4);
        list.push(c5);


        populate();
    }

    public void populate() {
        TextView loading = (TextView) findViewById(R.id.vote_loading);
        radioG.removeView(loading);

        final float scale = getResources().getDisplayMetrics().density;

        while( list.top() != null ) {
            RadioButton rd = new RadioButton(this);
            rd.setLayoutParams(new LinearLayout.LayoutParams(
                    (int)(300 * scale + 0.5f),
                    (int)(100 * scale + 0.5f)
            ));
            rd.setText(list.top().getName());
            rd.setTextSize(10 * scale + 0.5f);

            int resId = getResources().getIdentifier("id"+list.top().getPartyID(),"drawable", getPackageName());
            Drawable d = getResources().getDrawable(resId);
            rd.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);

            radioG.addView(rd);
            list.pop();
        }
//        radioG.addView();
    }

}
