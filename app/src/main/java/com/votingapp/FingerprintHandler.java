package com.votingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    int flag = 0;
    private CountDownTimer cdTimer2;
    //boolean flag = false;
    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        flag = flag + 1;
        if(flag > 0 && flag < 4 ) {
            this.update("Fingerprint Authentication failed\n" + (5-flag) + " More Chances", false);
        }
        else if(flag == 4) {
            this.update("Fingerprint Authentication failed \n 1 More Chance", false);
        }
        else {
            this.update("\nFingerprint Authentication failed \n Try again after sometime\n", false);
            cdTimer2 = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    ((Activity)context).finish();
                }
            };
            cdTimer2.start();
//            ((Activity)context).finish();
        }
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
    }


    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.fprintwarning);
        textView.setText(e);
        if(success){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            //flag = true;
            final Intent ie = new Intent(((Activity)context), MainActivity.class);
            cdTimer2 = new CountDownTimer(700, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    context.startActivity(ie);
                    ((Activity)context).finish();
                }
            };
            cdTimer2.start();
//
//            context.startActivity(ie);
//            ((Activity)context).finish();
        }
    }

}