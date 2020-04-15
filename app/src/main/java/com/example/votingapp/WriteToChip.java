package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.*;

public class WriteToChip extends AppCompatActivity {
    private Voter voter = null;
    private int partyID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_chip);
        Intent g = getIntent();
        voter = (Voter) g.getSerializableExtra("Voter");
        partyID = g.getIntExtra("PARTYID",9);

        //DIRECTLY GOING TO UPLOADTOSERVER
          goToUploadToServer();

        // Populate this string with the vote you want to save
        String vote="Arnab";
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath());
            if(dir.mkdirs())
            {
                Log.i("Write", "created");
                File file = new File(dir, "Vote.txt");

                FileOutputStream f = new FileOutputStream(file);
                f.write(vote.getBytes());
                f.close();
            }
            else {
                Toast.makeText(this,"Write To Chip Failed",Toast.LENGTH_LONG).show();
            }

        }catch(Exception e)
        {
            Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
        }
    }

    void goToUploadToServer(){
        Intent in = new Intent(this,UploadToServer.class);
        in.putExtra("Voter",voter);
        in.putExtra("PARTYID", partyID);
        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(WriteToChip.this, "Not Allowed", Toast.LENGTH_LONG).show();
    }
}
