package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class UploadToServer extends AppCompatActivity {
    private Voter voter = null;
    private int partyID;
    private TextView upDetails;
    private Button nextB;
    private String success = "Updated Successfully";
    private ProgressBar progressB;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_to_server);
        Intent g = getIntent();
        upDetails = (TextView)findViewById(R.id.upload);
        nextB = (Button) findViewById(R.id.viewButton);
        progressB = (ProgressBar) findViewById(R.id.progressBar);

        voter = (Voter) g.getSerializableExtra("Voter");
        partyID = g.getIntExtra("PARTYID", 10);
        if(partyID == 10)
        {
            Log.e("ERROR_PARTYID", "PartyID in uploadToServer to 10");
        }

        nextB.setEnabled(false);

        UploadToServer.ElectionDB ob = new UploadToServer.ElectionDB();
        ob.execute(voter.getEpic_no()+" "+partyID);
    }


    void updatedUIOnSuccess(){
        upDetails.setText(success);
        progressB.setVisibility(View.INVISIBLE);
        if(flag == 0)
        {
            nextB.setText("Log Out");
        }
        nextB.setEnabled(true);
    }

    protected void goToVerifyVote(View v) {
        Intent intent = new Intent(this, VerifyVote.class);
        intent.putExtra("Voter", voter);
        startActivity(intent);
        finish();
    }

    public class ElectionDB extends AsyncTask<String,String,Void> {
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
            nextB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag == 1) {
                        goToVerifyVote(v);
                        finish();
                    }
                    else{
                        Toast.makeText(UploadToServer.this, "INVALID", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });
            if(flag == 1)
                updatedUIOnSuccess();
        }
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(UploadToServer.this, "Not Allowed", Toast.LENGTH_SHORT).show();
    }
}
