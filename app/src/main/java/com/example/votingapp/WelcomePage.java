package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import android.os.Handler;

public class WelcomePage extends AppCompatActivity {

    private Voter voter=null;
    Button verifyV = null;
    Button castV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    void loadActivity(){
        setContentView(R.layout.activity_welcome_page);

        castV = (Button) findViewById(R.id.buttonCastVote);
        castV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVoterDetails(v);
            }
        });

        verifyV = (Button) findViewById(R.id.buttonVerifyVote);
        verifyV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVerifyVote(v);
            }
        });

        castV.setVisibility(View.INVISIBLE);
        verifyV.setVisibility(View.INVISIBLE);

        Intent g= getIntent();

        //Create  a voter class and intialise the constructor with epic
        String Epic=g.getStringExtra("EPIC");

        // Delete
        String Pass=g.getStringExtra("PASS");

        //get string data from VoterDB
        String voterDetails=g.getStringExtra("DETAILS");

        //EPIC NAME FATHER NAME  DOB  ADDR  SEX  CID  PASS
        String details[]=voterDetails.split("\\$");

        voter = new Voter(details[0], details[1], details[2], details[3], details[4], details[5], details[6], Pass);

        updateUI(voter.getEpic_no(), voter.getName());

//        Log.i("download1", voter.getEpic_no());

        // check with electionDB abhishek
        ElectionDB ob =new ElectionDB();
        ob.execute(voter.getEpic_no()+" "+Pass);
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
//            Intent i = new Intent(this,MainActivity.class);
//            i.putExtra("exit", "1");
//            startActivity(i);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    //Update the UI with the login
    public void updateUI(String epic,String voterName ){
        TextView epicno=(TextView)findViewById(R.id.epicno);
        TextView name=(TextView)findViewById(R.id.name);

        epicno.setText(epic);
        name.setText(voterName);
    }

    protected void goToVerifyVote(View v) {
        Intent intent = new Intent(this, VerifyVote.class);
        intent.putExtra("Voter", voter);
        startActivity(intent);
        finish();
    }

    protected void goToVoterDetails(View v) {
        Intent intent = new Intent(this, VoterDetails.class);
        intent.putExtra("Voter",voter);
        startActivity(intent);
        finish();
    }


    public class ElectionDB extends AsyncTask<String,String,String>
    {
        private String IP=Connect.IP;
        private int port=Connect.port;
        private Socket s=null;
        private DataOutputStream out=null;
        @Override
        protected String doInBackground(String... params) {

            String mssg=params[0];
            try{
                s= new Socket(IP, port);

                out=new DataOutputStream(s.getOutputStream());
                out.flush();
                out.writeUTF(2+" "+mssg);
                out.flush();

                s.close();

                s = new Socket(IP, port);
                InputStream in=s.getInputStream();

                DataInputStream d_in = new DataInputStream(in);
                String l = d_in.readUTF();

                in.close();
                s.close();
                return l;
            }catch (Exception e) {

            }
            return "INVALID";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String str) {
            str=str.trim();

            String details[]=str.split(" ");
            if(Integer.parseInt(details[2])==0) {
                voter.setAlreadyVoted(false);
            }
            if(!voter.getAlreadyVoted()) {
                Toast.makeText(WelcomePage.this, "Not voted", Toast.LENGTH_SHORT).show();
                castV.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(WelcomePage.this, "Already voted", Toast.LENGTH_SHORT).show();
                verifyV.setVisibility(View.VISIBLE);
            }
        }
    }
}
