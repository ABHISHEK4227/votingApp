package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class VoterDetails extends AppCompatActivity {
    private Voter voter=null;
    private String candidatesStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadActivity();
    }

    void loadActivity(){
        setContentView(R.layout.activity_voter_details);
        Intent g= getIntent();

        //Create  a voter class and intialise the constructor with epic
        voter = (Voter) g.getSerializableExtra("Voter");

        ((TextView)findViewById(R.id.vd_name)).setText(voter.getName());
        ((TextView)findViewById(R.id.vd_father_name)).setText(voter.getFather());
        ((TextView)findViewById(R.id.vd_dob)).setText(voter.getDob());
        ((TextView)findViewById(R.id.vd_sex)).setText(voter.getSex());
        ((TextView)findViewById(R.id.vd_address)).setText(voter.getAddress());

        ElectionDB ob = new ElectionDB();
        ob.execute(voter.getCid()+" "+voter.getPassword());

        Button b = (Button) findViewById(R.id.voterDetailsConfirm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCastVote(v);
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(VoterDetails.this, "Not Allowed", Toast.LENGTH_LONG).show();
//    }

    public void goToCastVote(View v){
        Intent i=new Intent(this,CastVote.class);
        i.putExtra("CANDIDATES",candidatesStr);
        i.putExtra("Voter", voter);
        startActivity(i);
    }

    public class ElectionDB extends AsyncTask<String,String,String>

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
        }
    }

}
