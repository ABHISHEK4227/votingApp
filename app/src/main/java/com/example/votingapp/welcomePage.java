package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class welcomePage extends AppCompatActivity {
static int a=7;
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

        // check with electionDB
        Boolean alreadyVoted = true;

        //get string data from VOterDB
        String voteDetails = "";
        Toast.makeText(this, "HEy HO", Toast.LENGTH_SHORT).show();

        //ob.send();

        connect ob=new connect();
        ob.execute("Ranjit Mullick");




    }





}
