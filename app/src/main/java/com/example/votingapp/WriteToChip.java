package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.*;

public class WriteToChip extends AppCompatActivity {
    private Voter voter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_chip);
        Intent g = getIntent();
        voter = (Voter) g.getSerializableExtra("Voter");
        // Populate this string with the vote you want to save
        String vote="Arnab";
        try {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, "Vote");

        FileOutputStream f = new FileOutputStream(file);
        f.write(vote.getBytes());
        f.close();

        }catch(Exception e)
        {
            Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(WriteToChip.this, "Not Allowed", Toast.LENGTH_LONG).show();
    }
}
