package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class VerifyVote extends AppCompatActivity {
    private Voter voter = null;
    private TextView candName = null;
    private ImageView imageView = null;
    private Button logout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        logout = (Button) findViewById(R.id.buttonUnknown);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void loadActivity(){
        setContentView(R.layout.activity_verify_vote);
        Intent g = getIntent();
        voter = (Voter) g.getSerializableExtra("Voter");

        ((TextView)findViewById(R.id.vv_epic)).setText(voter.getEpic_no());

        candName = (TextView) findViewById(R.id.candName);
        imageView = (ImageView) findViewById(R.id.imageView);

        candName.setText("LOADING...");
        imageView.setVisibility(View.INVISIBLE);

        VerifyVote.ElectionDB ob = new VerifyVote.ElectionDB();
        ob.execute(voter.getEpic_no()+" "+voter.getPassword());
    }

    public class ElectionDB extends AsyncTask<String,String,String>
    {
        private String IP=Connect.IP;
        private int port=Connect.port;
        private Socket s=null;
        private DataOutputStream out=null;
        @Override
        protected String doInBackground(String... params) {
            String mssg=params[0];
            try{
                s= new Socket(IP, port);

                out=new DataOutputStream(s.getOutputStream());
                out.flush();
                out.writeUTF(4+" "+mssg);
                out.flush();
                out.close();
                s.close();

                s = new Socket(IP, port);
                InputStream in=s.getInputStream();
                DataInputStream d_in = new DataInputStream(in);
                String l = d_in.readUTF();

                in.close();
                s.close();
                return l;
            }catch (Exception e) {
                return  "INVALID";
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String str) {
            str=str.trim();

            String details[]=str.split("\\$");

            if(!details[0].equals("INVALID")) {
                candName.setText(details[0]);
                int resId = getResources().getIdentifier("id"+details[1],"drawable", getPackageName());
                Drawable d = getResources().getDrawable(resId);
                imageView.setImageDrawable(d);
                imageView.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(VerifyVote.this, "INVALID", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
//            Intent i = new Intent(this,MainActivity.class);
//            i.putExtra("exit", "1");
//            startActivity(i);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to logout", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
