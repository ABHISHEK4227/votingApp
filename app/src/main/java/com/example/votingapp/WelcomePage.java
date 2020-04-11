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


        //get string data from VOterDB
        String voterDetails=g.getStringExtra("DETAILS");

        String details[]=voterDetails.split(" ");
        updateUI(details[0],details[1]);


        // check with electionDB abhishek
        Boolean alreadyVoted = true;










        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCastVote(v);
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



    protected void goToCastVote(View v) {
        Intent intent = new Intent(this, CastVote.class);
        startActivity(intent);


    }




}
