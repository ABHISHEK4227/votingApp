package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public  void sendData(View v){
        Intent i =new Intent(this,welcomePage.class);
            String epic=((EditText)findViewById(R.id.epic)).getText().toString();
            String pass=((EditText)findViewById(R.id.pass)).getText().toString();
            i.putExtra("EPIC",epic);
            i.putExtra("PASS",pass);
            startActivity(i);

    }


}
