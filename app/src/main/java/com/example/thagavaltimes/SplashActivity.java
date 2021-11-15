package com.example.thagavaltimes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);

            }
        },SPLASH_SCREEN);
*/


        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(4000);
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();


    }

    // Exit Msg Process Start    -------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit Conformation");
        alertDialog.setIcon(R.drawable.tt);
        alertDialog.setMessage("are you want to exit?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });
        //Alert Run Part
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
    // Back Process End    -------------------------------------------------------------------------

}