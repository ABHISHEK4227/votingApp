package com.votingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.votingapp.entities.Candidate;
import com.votingapp.entities.Voter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VoteManager{
    Context m_context;
    Voter voter;
    RadioGroup radioG;
    Boolean timeUp=false;
    CountDownTimer cdTimer;
    String candidatesStr="";

    public VoteManager(Context context, Voter vtr){
        voter = vtr;
        m_context = context;
    }

    public void goToWriteToChip(){
        radioG = (RadioGroup) ((Activity)m_context).findViewById(R.id.radiogroup_vote);
        int selected = radioG.getCheckedRadioButtonId();
        RadioButton rd = (RadioButton) ((Activity)m_context).findViewById(selected);
        Intent i=new Intent(((Activity)m_context).getApplicationContext(),WriteToChip.class);
        i.putExtra("Voter", voter);
        int tag = (int) rd.getTag();
        i.putExtra("PARTYID", tag);
        ((Activity)m_context).startActivity(i);
        ((Activity)m_context).finish();
    }

    public void startTimer() {
        cdTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView timeT = (TextView) ((Activity)m_context).findViewById(R.id.timeText);

                timeT.setText("Time left : " + millisUntilFinished / 1000 + " sec");
            }

            @Override
            public void onFinish() {
                TextView timeT = (TextView) ((Activity)m_context).findViewById(R.id.timeText);
                Button confirmButton = (Button) ((Activity)m_context).findViewById(R.id.confirmVoteButton);
                Toast.makeText(((Activity) m_context), "Time is Up!", Toast.LENGTH_SHORT).show();
                radioG = (RadioGroup) ((Activity)m_context).findViewById(R.id.radiogroup_vote);
                confirmButton.setText("Logout");
                timeT.setText("Time's Up!");
                radioG.setVisibility(View.INVISIBLE);
                timeUp = true;
            }
        };
        cdTimer.start();
    }

    public void checkRadioButton(){
        if(timeUp){
            ((Activity) m_context).finish();
        }
        else {
            radioG = (RadioGroup) ((Activity)m_context).findViewById(R.id.radiogroup_vote);
            int selected = radioG.getCheckedRadioButtonId();
            if (selected == -1) {
                Toast.makeText(((Activity) m_context), "Select one first!", Toast.LENGTH_SHORT).show();
                return;
            }
            cdTimer.cancel();
            goToWriteToChip();
        }
    }

    public void populate(String candDetails) {
        TextView loading = (TextView) ((Activity)m_context).findViewById(R.id.vote_loading);
        System.out.println(candDetails);
        radioG = (RadioGroup) ((Activity)m_context).findViewById(R.id.radiogroup_vote);
        radioG.removeView(loading);

        final float scale =  ((Activity)m_context).getResources().getDisplayMetrics().density;
        String candStringArray[] = candDetails.split("\\$");
        System.out.println('\n'+candStringArray[0]);
        int arrLen = candStringArray.length;
        System.out.println(arrLen);
        Candidate.CandidateList list = new Candidate.CandidateList(new Candidate(candStringArray[0], Integer.parseInt(candStringArray[1])));
        for(int i=2;i<arrLen;i+=2)
        {
            list.push(new Candidate(candStringArray[i], Integer.parseInt(candStringArray[i+1])));
        }
        while( list.top() != null ) {
            RadioButton rd = new RadioButton(((Activity)m_context));
            rd.setLayoutParams(new LinearLayout.LayoutParams(
                    (int)(300 * scale + 0.5f),
                    (int)(100 * scale + 0.5f)
            ));
            rd.setText(list.top().getName());
            rd.setTextSize(10 * scale + 0.5f);

            int resId = ((Activity)m_context).getResources().getIdentifier("id"+list.top().getPartyID(),"drawable", m_context.getPackageName());
            Drawable d = ((Activity)m_context).getResources().getDrawable(resId);
            rd.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);

            rd.setTag(list.top().getPartyID());

            radioG.addView(rd);
            list.pop();
        }
    }

    public void goToFacialRecognition(){
        ElectionDB ob = new ElectionDB();
        ob.execute(voter.getCid()+" "+voter.getPassword());
    }

    public class ElectionDB extends AsyncTask<String,String,String> {
        private String IP=Connect.IP;
        private int port=Connect.port;
        private Socket s=null;
        private ServerSocket server=null;

        private DataOutputStream out=null;
        @Override
        protected String doInBackground(String... params) {

            String mssg=params[0];
            try{
                s= new Socket(IP, port);

                out=new DataOutputStream(s.getOutputStream());
                out.flush();
                out.writeUTF(3+" "+mssg);
                out.flush();

                s.close();

                s=new Socket(IP,port);
                InputStream in=s.getInputStream();

//                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
//                String l=reader.readLine();
                DataInputStream d_in = new DataInputStream(in);
                String l = d_in.readUTF();

                in.close();
                s.close();

                return l;
            }catch (Exception e){

            }
            return IP;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String str) {
            candidatesStr=str.trim();
            System.out.println("adfaf" + candidatesStr);
            Intent i=new Intent(m_context,FacialRecognition.class);
            i.putExtra("CANDIDATES",candidatesStr);
            i.putExtra("Voter", voter);
            ((Activity)m_context).startActivity(i);
            ((Activity)m_context).finish();
        }
    }

    public void uploadVote(int partyID){
        ElectionDB2 ob = new ElectionDB2();
        ob.execute(voter.getEpic_no()+" "+partyID);
    }

    String success = "Uploaded Successfully";
    int flag = 1;

    public void updatedUI(){
        TextView upDetails = (TextView) ((Activity)m_context).findViewById(R.id.upload);
        Button nextB = (Button) ((Activity)m_context).findViewById(R.id.viewButton);
        ProgressBar progressB = (ProgressBar) ((Activity)m_context).findViewById(R.id.progressBar);
        upDetails.setText(success);
        progressB.setVisibility(View.INVISIBLE);
        if(flag == 0)
        {
            nextB.setText("Log Out");
        }
        nextB.setEnabled(true);
    }

    public void goToVerifyVote(View v) {
        Intent intent = new Intent(((Activity)m_context), VerifyVote.class);
        intent.putExtra("Voter", voter);
        ((Activity)m_context).startActivity(intent);
        ((Activity)m_context).finish();
    }

    public class ElectionDB2 extends AsyncTask<String,String,Void> {
        private String IP = Connect.IP;
        private int port = Connect.port;
        private Socket s = null;
        private DataOutputStream out = null;

        @Override
        protected Void doInBackground(String... params) {
            String mssg = params[0];
            try {
                s = new Socket(IP, port);

                out = new DataOutputStream(s.getOutputStream());
                out.flush();
                out.writeUTF(6 + " " + mssg);
                out.flush();
                s.close();

                s = new Socket(IP, port);
                InputStream in = s.getInputStream();
                DataInputStream d_in = new DataInputStream(in);
                String l = d_in.readUTF();

                if(l.equals("INVALID"))
                {
                    success = "Unsuccessful";
                    flag = 0;
//                    Toast.makeText(m_context, "INVALID", Toast.LENGTH_LONG).show();
                }

                in.close();
                s.close();
            } catch (Exception e) {
                success = "Unsuccessful";
                flag = 0;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void v) {
            Button nextB = (Button) ((Activity)m_context).findViewById(R.id.viewButton);
            nextB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag == 1) {
                        goToVerifyVote(v);
                        ((Activity)m_context).finish();
                    }
                    else{
                        ((Activity)m_context).finish();
                    }
                }
            });
            updatedUI();
        }
    }
}