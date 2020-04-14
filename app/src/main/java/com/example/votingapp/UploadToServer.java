package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UploadToServer extends AppCompatActivity {
    TextView upDetails;
    Button nextB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_to_server);
        upDetails = (TextView)findViewById(R.id.upload);
        nextB = (Button) findViewById(R.id.viewButton);
        nextB.setEnabled(false);
        if(uploadToElectionDB()){
            upDetails.setText("Updated Successfully");
            nextB.setEnabled(true);
        }
        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // goToVerifyVote(v);
                finish();
            }
        });
    }

//    protected void goToVerifyVote(View v) {
//        Intent intent = new Intent(this, VerifyVote.class);
//        intent.putExtra("Voter", voter);
//        startActivity(intent);
//        finish();
//    }
    protected boolean uploadToElectionDB(){
        return true;
    }

}
