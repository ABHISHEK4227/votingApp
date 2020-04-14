package com.example.votingapp;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CastVote extends AppCompatActivity {
    private Voter voter;
    Button confirmButton;
    private RadioGroup radioG;
    private TextView timeT;
    boolean timeUp = false;
    // fetch Candidate List from voteManager
    private CandidateList list;
    private CountDownTimer cdTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        Intent g=getIntent();
        String candidateDetails = g.getStringExtra("CANDIDATES");
        voter = (Voter) g.getSerializableExtra("Voter");
        list = null;
        confirmButton = (Button) findViewById(R.id.confirmVoteButton);
        radioG = (RadioGroup) findViewById(R.id.radiogroup_vote);

        populate(candidateDetails);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeUp){
                    finish();
                }
                else{
                    cdTimer.cancel();
                    goToWriteToChip();
                }
            }
        });


        timeT = (TextView) findViewById(R.id.timeText);
        cdTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeT.setText("Time left : " + millisUntilFinished/1000 + " sec");
            }

            @Override
            public void onFinish() {
                openDiag();
                confirmButton.setText("Logout");
                timeUp = true;
            }
        };
        cdTimer.start();

    }

    public void populate(String candDetails) {
        TextView loading = (TextView) findViewById(R.id.vote_loading);
        radioG.removeView(loading);

        final float scale = getResources().getDisplayMetrics().density;
        String candStringArray[] = candDetails.split("\\$");

        int arrLen = candStringArray.length;
        System.out.println(arrLen);
        list = new CandidateList(new Candidate(candStringArray[0], Integer.parseInt(candStringArray[1])));
        for(int i=2;i<arrLen;i+=2)
        {
            list.push(new Candidate(candStringArray[i], Integer.parseInt(candStringArray[i+1])));
        }
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

            rd.setTag(list.top().getPartyID());

            radioG.addView(rd);
            list.pop();
        }
    }

    public void openDiag(){
        CdtDialog diag = new CdtDialog();
        diag.show(getSupportFragmentManager(),"Timer");
    }

    public void goToWriteToChip(){
        RadioButton rd = findViewById(radioG.getCheckedRadioButtonId());
        Intent i=new Intent(getApplicationContext(),WriteToChip.class);
        i.putExtra("Voter", voter);
        int tag = (int) rd.getTag();
        i.putExtra("PARTYID", tag);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(CastVote.this, "Not Allowed", Toast.LENGTH_LONG).show();
    }
}
