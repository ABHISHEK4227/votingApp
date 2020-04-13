package com.example.votingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class FacialRecognition extends AppCompatActivity {
    private String candidatesStr;
    private Voter voter;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView capturedImage;
    int countFc = 0;
    String capImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_recognition);

        Intent g=getIntent();
        candidatesStr = (String) g.getStringExtra("CANDIDATES");
        voter = (Voter) g.getSerializableExtra("Voter");

        Button b = (Button) findViewById(R.id.faceRecSubmitButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendImageToServer()) {
                    goToCastVote(v);
                }
                else{
                    countFc++;
                    if(countFc >= 3){
                        Intent i = new Intent( FacialRecognition.this, MainActivity.class);
                        //i.putExtra("CANDIDATES",str);
                        startActivity(i);
                    }
                    else{
                        capturedImage = (ImageView) findViewById(R.id.capturedImage);
                        capturedImage.setImageDrawable(null);

                        TextView tv = (TextView) findViewById(R.id.facerecogWarning);

                        final Button fcCapture = (Button) findViewById(R.id.faceRecCaptureButton);
                        fcCapture.setEnabled(true);

                        if(countFc==1) {
                            tv.setText("FAILED! You have 2 chances left");
                        }
                        else if(countFc==2){
                            tv.setText("FAILED! You have 1 chance left");
                        }

                    }
                }
            }
        });

        final Button fcCapture = (Button) findViewById(R.id.faceRecCaptureButton);
        fcCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
                {
                    //fcCapture.setEnabled(false);
                    launchCamera1();
                }
            }
        });

        capturedImage = (ImageView) findViewById(R.id.capturedImage);

    }

    @Override
    protected void onResume() {
        super.onResume();
        capturedImage = (ImageView) findViewById(R.id.capturedImage);
        TextView tv = (TextView) findViewById(R.id.facerecogWarning);
        final Button fcCapture = (Button) findViewById(R.id.faceRecCaptureButton);
        if(capturedImage.getDrawable() != null)
        {
            fcCapture.setEnabled(false);
            tv.setText("Image has been captured");
        }
    }


    public void goToCastVote(View v){
        Intent i=new Intent(getApplicationContext(),CastVote.class);
        i.putExtra("CANDIDATES",candidatesStr);
        i.putExtra("Voter", voter);
        startActivity(i);
        finish();
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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void launchCamera1() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            capturedImage.setImageBitmap(imageBitmap);
            capImage = convert(imageBitmap);
        }

    }

    // method for sending string image to server
    public boolean sendImageToServer(){
        // send(capImage);
        System.out.println(capImage.length());
        return true;
    }


    // method for converting bitmap to string base 64
    public String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


}
