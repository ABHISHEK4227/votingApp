package com.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.votingapp.entities.Voter;

public class VoterDetails extends AppCompatActivity {
    private Voter voter=null;
    private String candidatesStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
        TextView contactECI = (TextView) findViewById(R.id.buttonContactECI);
        contactECI.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    void loadActivity(){
        setContentView(R.layout.activity_voter_details);
        Intent g= getIntent();

        voter = (Voter) g.getSerializableExtra("Voter");
        final VoteManager vManager = new VoteManager(this, voter);

        ((TextView)findViewById(R.id.vd_name)).setText(voter.getName());
        ((TextView)findViewById(R.id.vd_father_name)).setText(voter.getFather());
        ((TextView)findViewById(R.id.vd_dob)).setText(voter.getDob());
        ((TextView)findViewById(R.id.vd_sex)).setText(voter.getSex());
        ((TextView)findViewById(R.id.vd_address)).setText(voter.getAddress());

//        VoterDetails.ElectionDB ob = new VoterDetails.ElectionDB();
//        ob.execute(voter.getCid()+" "+voter.getPassword());

        final Button b = (Button) findViewById(R.id.voterDetailsConfirm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vManager.goToFacialRecognition();
                b.setEnabled(false);
            }
        });

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
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

//    public void goToFacialRecognition(View v){
//        Intent i=new Intent(this,FacialRecognition.class);
//        i.putExtra("CANDIDATES",candidatesStr);
//        i.putExtra("Voter", voter);
//        startActivity(i);
//        finish();
//    }

//    public class ElectionDB extends AsyncTask<String,String,String>
//
//    {
//        private String IP=Connect.IP;
//        private int port=Connect.port;
//        private Socket s=null;
//        private ServerSocket server=null;
//
//        private DataOutputStream out=null;
//        @Override
//        protected String doInBackground(String... params) {
//
//            String mssg=params[0];
//            try{
//                s= new Socket(IP, port);
//
//                out=new DataOutputStream(s.getOutputStream());
//                out.flush();
//                out.writeUTF(3+" "+mssg);
//                out.flush();
//
//                s.close();
//
//                s=new Socket(IP,port);
//                InputStream in=s.getInputStream();
//
////                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
////                String l=reader.readLine();
//                DataInputStream d_in = new DataInputStream(in);
//                String l = d_in.readUTF();
//
//                in.close();
//                s.close();
//
//                return l;
//            }catch (Exception e){
//
//            }
//            return IP;
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//
//        }
//
//        @Override
//        protected void onPostExecute(String str) {
//            candidatesStr=str.trim();
//        }
//    }

}
