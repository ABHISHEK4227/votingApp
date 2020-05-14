package com.votingapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.votingapp.entities.Voter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class VerifyVoteManager  {
    private Voter voter = null;
    private TextView candName = null;
    private ImageView imageView = null;
    private Button logout = null;
    private Context context=null;

    VerifyVoteManager(Context context,Voter voter){
        this.context=context;
        this.voter=voter;
    }

    public void  startManager(){
        candName = (TextView)((Activity)context).findViewById(R.id.candName);
        imageView = (ImageView)((Activity)context).findViewById(R.id.imageView);

        candName.setText("LOADING...");
        imageView.setVisibility(View.INVISIBLE);
        ElectionDB ob = new ElectionDB();
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
                int resId = context.getResources().getIdentifier("id"+details[1],"drawable", context.getPackageName());
                Drawable d = context.getResources().getDrawable(resId);
                imageView.setImageDrawable(d);
                imageView.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(((Activity)context), "INVALID", Toast.LENGTH_SHORT).show();
                ((Activity)(context)).finish();
            }
        }
    }
}
