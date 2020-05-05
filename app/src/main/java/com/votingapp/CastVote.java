package com.votingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import com.votingapp.entities.Voter;

public class CastVote extends AppCompatActivity {
    private Voter voter;
    private Button confirmButton;
    private TextView timeT;
//    private RadioGroup radioG;
//    boolean timeUp = false;
    // fetch Candidate List from voteManager
//    private CandidateList list;
//    private CountDownTimer cdTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);

        Intent g=getIntent();

        String candidateDetails = g.getStringExtra("CANDIDATES");
        voter = (Voter) g.getSerializableExtra("Voter");
//        list = null;
        //        radioG = findViewById(R.id.radiogroup_vote);
        confirmButton = findViewById(R.id.confirmVoteButton);

        final VoteManager vManager = new VoteManager(this,voter);

        vManager.populate(candidateDetails);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(timeUp){
//                    finish();
//                }
//                else{
//                    int selected = radioG.getCheckedRadioButtonId();
//                    if(selected == -1)
//                    {
//                        Toast.makeText(CastVote.this, "Select one first!", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    cdTimer.cancel();
//                    goToWriteToChip();
//                }
                vManager.checkRadioButton();
            }
        });

        timeT = (TextView) findViewById(R.id.timeText);
//        cdTimer = new CountDownTimer(20000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timeT.setText("Time left : " + millisUntilFinished/1000 + " sec");
//            }
//
//            @Override
//            public void onFinish() {
//                openDiag();
//                confirmButton.setText("Logout");
//                timeT.setText("Time's Up!");
//                radioG.setVisibility(View.INVISIBLE);
//                timeUp = true;
//            }
//        };
//        cdTimer.start();
//        CdtDialog diag = new CdtDialog();
        vManager.startTimer();
    }

//    public void populate(String candDetails) {
//        TextView loading = (TextView) findViewById(R.id.vote_loading);
//        radioG.removeView(loading);
//
//        final float scale = getResources().getDisplayMetrics().density;
//        String candStringArray[] = candDetails.split("\\$");
//
//        int arrLen = candStringArray.length;
//        System.out.println(arrLen);
//        list = new CandidateList(new Candidate(candStringArray[0], Integer.parseInt(candStringArray[1])));
//        for(int i=2;i<arrLen;i+=2)
//        {
//            list.push(new Candidate(candStringArray[i], Integer.parseInt(candStringArray[i+1])));
//        }
//        while( list.top() != null ) {
//            RadioButton rd = new RadioButton(this);
//            rd.setLayoutParams(new LinearLayout.LayoutParams(
//                    (int)(300 * scale + 0.5f),
//                    (int)(100 * scale + 0.5f)
//            ));
//            rd.setText(list.top().getName());
//            rd.setTextSize(10 * scale + 0.5f);
//
//            int resId = getResources().getIdentifier("id"+list.top().getPartyID(),"drawable", getPackageName());
//            Drawable d = getResources().getDrawable(resId);
//            rd.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
//
//            rd.setTag(list.top().getPartyID());
//
//            radioG.addView(rd);
//            list.pop();
//        }
//    }

//    public void openDiag(){
//        CdtDialog diag = new CdtDialog();
//        diag.show(getSupportFragmentManager(),"Timer");
//    }

//    public void goToWriteToChip(){
//        int selected = radioG.getCheckedRadioButtonId();
//        RadioButton rd = findViewById(selected);
//        Intent i=new Intent(getApplicationContext(),WriteToChip.class);
//        i.putExtra("Voter", voter);
//        int tag = (int) rd.getTag();
//        i.putExtra("PARTYID", tag);
//        startActivity(i);
//        finish();
//    }

    @Override
    public void onBackPressed() {
        Toast.makeText(CastVote.this, "Not Allowed", Toast.LENGTH_SHORT).show();
    }
}
