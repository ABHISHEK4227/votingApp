package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WelcomePage extends AppCompatActivity {

    private Socket s=null;
    private ServerSocket server=null;

    private DataOutputStream out=null;
    private int port= 9000;
    private String IP="192.168.0.100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Intent g= getIntent();

        //Create  a voter class and intialise the constructor with epic
        String Epic=g.getStringExtra("EPIC");

        // Delete
        String Pass=g.getStringExtra("PASS");


        //get string data from VOterDB (UNCOMMENT LATER)
//        String voterDetails=g.getStringExtra("DETAILS");
//
//        String details[]=voterDetails.split(" ");
//        updateUI(details[0],details[1]);


        // check with electionDB abhishek
        Boolean alreadyVoted = true;

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

    protected void goToVoterDetails(View v) {
        Intent intent = new Intent(this, CastVote.class);
        startActivity(intent);
    }


    protected void goToVerifyVote(View v) {
        Intent intent = new Intent(this, VerifyVote.class);
        startActivity(intent);
    }

}
