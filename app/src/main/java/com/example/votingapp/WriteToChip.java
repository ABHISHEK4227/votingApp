package com.example.votingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WriteToChip extends AppCompatActivity {
    private TextView textWarning;
    private Button btnExit;
    private boolean flag = true;
    private String str="savedVoteEpicNo";
    private ProgressBar pBar;
    private Voter voter;
    private int partyID;
    public boolean writeSDCardFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_chip);

        textWarning = (TextView) findViewById(R.id.textView18);
        btnExit = (Button) findViewById(R.id.buttonExit);
        //btnExit.setVisibility(View.INVISIBLE);
        pBar = findViewById(R.id.progressBar);

        Intent g = getIntent();
        voter = (Voter) g.getSerializableExtra("Voter");
        partyID = g.getIntExtra("PARTYID",10);
        if(partyID == 10){
            textWarning.setText("Error");
            textWarning.setTextColor(Color.RED);
            btnExit.setText("Logout");
            pBar.setVisibility(View.INVISIBLE);
            finish();
        }
        str = voter.getEpic_no() + "$" + partyID;

        //DIRECTLY GOING TO UPLOADTOSERVER
//        goToUploadToServer();

        // Populate this string with the vote you want to save
//        String vote="Arnab";
//        try {
//            File sdCard = Environment.getExternalStorageDirectory();
//            File dir = new File (sdCard.getAbsolutePath());
//            if(dir.mkdirs())
//            {
//                Log.i("Write", "created");
//                File file = new File(dir, "Vote.txt");
//
//                FileOutputStream f = new FileOutputStream(file);
//                f.write(vote.getBytes());
//                f.close();
//            }
//            else {
//                System.out.println("Didn't work!");
//            }
//
//        }catch(Exception e)
//        {
//            Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
//        }



        btnExit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!flag) {
                    finish();
                }
                else{
                    writeChipN(str);
                    if(writeSDCardFlag==true) {
                        goToUploadToServer();
                    }
                }
            }
        });

        if(!externalMemoryAvailable()){
            textWarning.setText("Chip is not Inserted");
            textWarning.setTextColor(Color.RED);
            btnExit.setText("Logout");
            pBar.setVisibility(View.INVISIBLE);
            flag = false;
        }
        else if(!checkPermission()){
            textWarning.setText("Chip is Inaccessible");
            textWarning.setTextColor(Color.RED);
            btnExit.setText("Logout");
            pBar.setVisibility(View.INVISIBLE);
            flag = false;
        }
//        else{
//            writeChip(str);
//            if(writeSDCardFlag==true){
//                goToUploadToServer();
//            }
        else if(!writeSDCardFlag){
            textWarning.setText("WriteToChip Failed");
            textWarning.setTextColor(Color.RED);
            btnExit.setText("Logout");
            pBar.setVisibility(View.INVISIBLE);
            flag = false;
        }

    }

    private boolean externalMemoryAvailable() {
        if (Environment.isExternalStorageRemovable()) {
            //device support sd card. We need to check sd card availability.
            String state = Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED) || state.equals(
                    Environment.MEDIA_MOUNTED_READ_ONLY);
        }
        else {
            //device not support sd card.
            return false;
        }
    }
//
//    String secStore = System.getenv("SECONDARY_STORAGE");
//    File f_secs = new File(secStore);


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void writeChipN(String str){
//        File dir1 = new File(Environment.getExternalStorageDirectory(), "savedVote.txt");
//        System.out.println(dir1);
//        try{
//            String secStore = System.getenv("EXTERNAL_STORAGE");
//            System.out.println(secStore);
//            File dir = new File(secStore);
//
//            FileOutputStream fos = new FileOutputStream(dir);
//            fos.write(str.getBytes());
//            fos.close();
//            Toast.makeText(this,"Saved in Chip Successfully", Toast.LENGTH_SHORT).show();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            return false;
//        }
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, 42);
        //return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == RESULT_OK) {
            Uri treeUri = resultData.getData();
            assert treeUri != null;
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);

            // List all existing files inside picked directory
            for (DocumentFile file : pickedDir.listFiles()) {
                Log.d("TAGG", "Found file " + file.getName() + " with size " + file.length());
            }

            // Create a new file and write into it
            try {
                assert pickedDir != null;
                DocumentFile newFile = pickedDir.createFile("text/plain", "My Novel");
                OutputStream out = getContentResolver().openOutputStream(newFile.getUri());
                out.write("A long time ago...".getBytes());
                out.close();
                writeSDCardFlag=true;
                flag = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                writeSDCardFlag=false;
                flag = false;
            }
        }
    }


    boolean checkPermission(){
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    void goToUploadToServer(){
        Intent in = new Intent(this,UploadToServer.class);
        in.putExtra("Voter",voter);
        in.putExtra("PARTYID", partyID);
        startActivity(in);
       // finish();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(WriteToChip.this, "Not Allowed", Toast.LENGTH_LONG).show();
    }
}
