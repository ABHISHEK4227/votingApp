package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private String epic="";
    private String pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(v);
            }
        });
    }
    public  void sendData(View v){


        epic=((EditText)findViewById(R.id.epic)).getText().toString();
        pass=((EditText)findViewById(R.id.pass)).getText().toString();
        Connect ob=new Connect();
        ob.execute(epic+" "+pass);

    }



    public class Connect extends AsyncTask<String,String,String>

    {
        private String IP="192.168.0.110";
        private Socket s=null;
        private ServerSocket server=null;

        private DataOutputStream out=null;
        @Override
        protected String doInBackground(String... params) {

            String mssg=params[0];
            try{
                s= new Socket(IP, 9000);

                out=new DataOutputStream(s.getOutputStream());
                out.flush();
                out.writeUTF("1 "+mssg);
                out.flush();

                s.close();



                s=new Socket(IP,9000);
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
            String str=values[0];
            str=str.trim();



            if(str.equals("INVALID")){
                Toast.makeText(MainActivity.this,"Invalid Login Details",Toast.LENGTH_LONG).show();
            }else {
                str=str.substring(1);
                Intent i =new Intent(MainActivity.this,WelcomePage.class);
                i.putExtra("EPIC",epic);
                i.putExtra("PASS",pass);
                i.putExtra("DETAILS",str);

                startActivity(i);

            }



        }

        @Override
        protected void onPostExecute(String s) {

        }
    }



}
