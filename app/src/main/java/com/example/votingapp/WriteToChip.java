package com.example.votingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteToChip extends AppCompatActivity {
    private TextView textWarning;
    private Button btnExit;
    private boolean flag = true;
    private String str="savedVoteEpicNo";
    private ProgressBar pBar;
    private Voter voter;
    private int partyID;
    private boolean writeSDCardFlag = false;
    private int countClick = 0;
    private Calendar calendar;
    private  SimpleDateFormat simpleDateFormat;
    private String uriPath = "/document/3F5C-09FB:savedVote.txt";
    private String filePath = "/storage/3F5C-09FB/savedVote.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_chip);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEEE , dd-MMM-yyyy hh:mm:ss a");



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

        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!flag) {
                    finish();
                }
                else{
                    countClick++;
                    if(countClick == 1) {
                        writeChipN(str);
                    }
                    else if(countClick == 2){
                        if(writeSDCardFlag) {
                            goToUploadToServer();
                        }
                        else{
                            finish();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(countClick == 2)
//        {
//
//        }
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


//        else{
//            writeChip(str);
//            if(writeSDCardFlag==true){
//                goToUploadToServer();
//            }

    }

    private boolean externalMemoryAvailable() throws IOException {
        File file = new File(filePath);
        return file.exists();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void writeChipN(String str){
        countClick--;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(intent, 5);
        //createFile(null);
    }

//    public void openDirectory(Uri uriToLoad) {
//        // Choose a directory using the system's file picker.
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//
//        // Provide read access to files and sub-directories in the user-selected
//        // directory.
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//
//        // Optionally, specify a URI for the directory that should be opened in
//        // the system file picker when it loads.
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
//
//        startActivityForResult(intent, 42);
//        System.out.println("ABCD");
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == 5
                && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                System.out.println(uri.toString() + " uriString");
                System.out.println(uri.getPath() + " path");
                // Perform operations on the document using its URI.
                alterDocument(uri);
            }
        }
    }

    private void alterDocument(Uri uri) {
        countClick++;
        try {
            ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(("Vote saved at, " + simpleDateFormat.format(calendar.getTime()) + "\n" + str).getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
            if( uriPath.equals(uri.getPath()) ) {
                writeSDCardFlag = true;
                textWarning.setText("WriteToChip Successful");
                textWarning.setTextColor(Color.RED);
                btnExit.setText("Next");
                pBar.setVisibility(View.INVISIBLE);
            }
            else {
                textWarning.setText("Please select the correct file");
                countClick--;
                textWarning.setTextColor(Color.RED);
            }

        } catch (FileNotFoundException e) {
            textWarning.setText("WriteToChip Failed");
            textWarning.setTextColor(Color.RED);
            btnExit.setText("Logout");
            pBar.setVisibility(View.INVISIBLE);
            flag = false;
            e.printStackTrace();

        } catch (IOException e) {
            textWarning.setText("WriteToChip Failed");
            textWarning.setTextColor(Color.RED);
            btnExit.setText("Logout");
            pBar.setVisibility(View.INVISIBLE);
            flag = false;
            e.printStackTrace();
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
        finish();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(WriteToChip.this, "Not Allowed", Toast.LENGTH_LONG).show();
    }
}