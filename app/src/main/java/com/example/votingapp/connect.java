package com.example.votingapp;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class connect extends AsyncTask<String,Void,String>

{
    private String IP="192.168.0.100";
    private Socket s=null;
    private ServerSocket server=null;

    private DataOutputStream out=null;
    @Override
    protected String doInBackground(String... params) {

        String mssg=params[0];
        try{
            s= new Socket(IP, 9000);

            out=new DataOutputStream(s.getOutputStream());


        }catch (Exception e){

        }

        try{


            out.writeUTF(mssg);
            out.close();
        }catch (Exception e){

        }
        return IP;
    }

    @Override
    protected void onPostExecute(String s) {

    }
}
