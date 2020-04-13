package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WelcomePage extends AppCompatActivity {

    private Socket s=null;
    private ServerSocket server=null;

    private DataOutputStream out=null;
    private int port= 9000;
    private String IP="192.168.0.110";
    private Boolean alreadyVoted;
    private String Epic="";
    private String Pass="";
    private String Cid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Intent g= getIntent();

        //Create  a voter class and intialise the constructor with epic
        Epic=g.getStringExtra("EPIC");

        // Delete
        Pass=g.getStringExtra("PASS");


        //get string data from VOterDB
        String voterDetails=g.getStringExtra("DETAILS");


        //EPIC NAME FATHER NAME  DOB  ADDR  SEX  CID  PASS
        String details[]=voterDetails.split("\\$");


        Cid=details[6];

        updateUI(details[0],details[1]);


        // check with electionDB abhishek
        alreadyVoted = true;
        Connect2 ob =new Connect2();
        ob.execute(Epic+" "+Pass);



        Button castV = (Button) findViewById(R.id.buttonCastVote);
        castV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVoterDetails(v);
            }
        });

        Button verifyV = (Button) findViewById(R.id.buttonVerifyVote);
        verifyV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVerifyVote(v);
            }
        });
    }

    //Update the UI with the login
    public void updateUI(String epic,String voterName ){
        TextView epicno=(TextView)findViewById(R.id.epicno);
        TextView name=(TextView)findViewById(R.id.name);

        epicno.setText(epic);
        name.setText(voterName);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    protected void goToVerifyVote(View v) {
        Intent intent = new Intent(this, VerifyVote.class);
        intent.putExtra("Epic",Epic);
        startActivity(intent);
    }

    protected void goToVoterDetails(View v) {
        Intent intent = new Intent(this, VoterDetails.class);
        intent.putExtra("CID",Cid);
        intent.putExtra("PASS",Pass);
        startActivity(intent);
    }


    public class Connect2 extends AsyncTask<String,String,String>

    {
        private String IP="192.168.0.110";
        private int port=9000;
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
                out.writeUTF(2+" "+mssg);
                out.flush();

                s.close();



                s=new Socket(IP,port);
                InputStream in=s.getInputStream();

                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String l=reader.readLine();





                publishProgress(l);


            }catch (Exception e){

            }


            return IP;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String str=values[0];
            str=str.trim();

            String details[]=str.split(" ");

            if(Integer.parseInt(details[2])==0)

                alreadyVoted=false;
        }

        @Override
        protected void onPostExecute(String s) {
            if(alreadyVoted==false)
            Toast.makeText(WelcomePage.this,"Not voted",Toast.LENGTH_SHORT).show();

        }
    }




}
