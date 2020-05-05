package com.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private String epic="";
    private String pass="";
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        flag = 0;
        loadActivity();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        flag = 1;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText pass = (EditText) findViewById(R.id.pass);
        pass.setText("");
    }

    void loadActivity(){
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
        MainActivity.VoterDB ob=new MainActivity.VoterDB();
        ob.execute(epic+" "+pass);
    }

    public class VoterDB extends AsyncTask<String,String,String> {
        private String IP = "192.168.0.110";
        private Socket s = null;
        private ServerSocket server = null;

        private DataOutputStream out = null;

        @Override
        protected String doInBackground(String... params) {

            String mssg = params[0];
            try {
                s = new Socket(Connect.IP, Connect.port);

                out = new DataOutputStream(s.getOutputStream());
                out.flush();
                out.writeUTF("1 " + mssg);
                out.flush();

                s.close();

                s = new Socket(Connect.IP, Connect.port);
                InputStream in = s.getInputStream();

//                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
//                String l=reader.readLine();
                DataInputStream d_in = new DataInputStream(in);
                String details = d_in.readUTF();

//                Log.i("Download", l);
                in.close();
                s.close();
                return details;
            } catch (Exception e) {

            }
            return "INVALID";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String str) {
            str = str.trim();

            if (str.equals("INVALID")) {
                Toast.makeText(MainActivity.this, "Invalid Login Details", Toast.LENGTH_LONG).show();
            }
            else {
                Intent i = new Intent(MainActivity.this, WelcomePage.class);
                i.putExtra("EPIC", epic);
                i.putExtra("PASS", pass);
                i.putExtra("DETAILS", str);

                startActivity(i);
            }

        }
    }
}
