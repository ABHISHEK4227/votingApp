package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class VoterDetails extends AppCompatActivity {

    private String Cid="";
    private String Pass="";
    private String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_details);
        Intent g= getIntent();

        //Create  a voter class and intialise the constructor with epic
        Cid=g.getStringExtra("CID");

        // Delete
        Pass=g.getStringExtra("PASS");
        Connect3 ob=new Connect3();
        ob.execute(Cid+" "+Pass);

        Button b = (Button) findViewById(R.id.confirm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCasteVote(v);
            }
        });

    }
    public void goToCasteVote(View v){
        Intent i=new Intent(this,CastVote.class);
        i.putExtra("CANDIDATES",str);
        startActivity(i);
    }


    public class Connect3 extends AsyncTask<String,String,String>

    {
        private String IP="192.168.0.105";
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
            str=values[0];
            str=str.trim();




        }

        @Override
        protected void onPostExecute(String s) {


        }
    }

}
