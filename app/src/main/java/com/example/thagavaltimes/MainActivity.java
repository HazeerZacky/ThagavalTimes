package com.example.thagavaltimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Initialize Scrole Bar Button
    Button btnAll, btnThagavalToday, btnLineToday, btnOnThisDay, btnWordToday;

    //Initialize Admob Banner Ad
    private AdView adView;

    //Initialize Admob interstitial Ad
    private InterstitialAd mInterstitialAd ;


    //Initialize variable
    DrawerLayout drawerLayout;

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    //FloatingActionButton floatingActionButton;

    //----------------------------------------------------------------------------------------------
    //------------------------------------- [ onCreate Start ] -------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttor Declare
        btnAll = (Button)findViewById(R.id.btnAll);
        btnLineToday = (Button)findViewById(R.id.btnLineToday);
        btnOnThisDay = (Button)findViewById(R.id.btnOnThisDay);
        btnThagavalToday = (Button)findViewById(R.id.btnThagavalToday);
        btnWordToday = (Button)findViewById(R.id.btnWordToday);

        // Navigation Part
        drawerLayout = findViewById(R.id.drawerLayout);

        //Database Part
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ReverseLayout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        //----------------------------------------------------------------------------

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("posts"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);


        //Admob Banner ad Start
        adView = findViewById(R.id.home_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //--------------------------------- [ Network Check Start ] --------------------------------



        //Initialize connectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get active nework info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Check n/w status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Button btnTryAgain = dialog.findViewById(R.id.bt_try_again);

            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();

        }else {

        }
        //---------------------------------- [ Network Check End ] ---------------------------------

        //Category Button Onclick
        //Button All
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRecyclerOptions<MainModel> options =
                        new FirebaseRecyclerOptions.Builder<MainModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("posts"), MainModel.class)
                                .build();

                mainAdapter = new MainAdapter(options);
                mainAdapter.startListening();
                recyclerView.setAdapter(mainAdapter);
            }
        });

        //Button Thagaval Today
        btnThagavalToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search("Thagaval Today");
            }
        });

        //Button On This Day
        btnOnThisDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search("On This Day");
            }
        });

        //Button Line Today
        btnLineToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search("Line Today");
            }
        });

        //Button Word Today
        btnWordToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search("Word Today");
            }
        });


    }

    //-------------------------------------- [ onCreate End ] --------------------------------------
    //----------------------------------------------------------------------------------------------

    private void Search(String str){
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("posts").orderByChild("category").equalTo(str), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    //----------------------------------------------------------------------------------------------

    //----------------------------- [ Navigation Part Start ] -----------------------------
    public void ClickMenu(View view) {
        //open
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        //open
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickLiveNews(View view) {
        redirectActivity(this,LiveNews.class);
    }

    public void ClickCoronaLiveTicker(View view) {
        redirectActivity(this,CoronaLiveTicker.class);
    }

    public void ClickCategory(View view) {
        redirectActivity(this,Category.class);
    }

    public void ClickInstagram(View view) {
        redirectActivity(this,Instagram.class);
    }

    public void ClickSettings(View view) {
        redirectActivity(this,Settings.class);
    }

    public void ClickSupport(View view) {
        redirectActivity(this,Support.class);
    }

    public void ClickAboutUs(View view) {
        redirectActivity(this,AboutUs.class);
    }

    public void ClickLogin(View view) {
        redirectActivity(this,Login.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    //          -------------------- [ Navigation Part End ] --------------------

    //----------------------------------------------------------------------------------------------



    //            ------------------ [ Exit Msg  Part Start ] ------------------
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit Conformation");
        alertDialog.setIcon(R.drawable.danger);
        alertDialog.setMessage("Are you sure you want to exit?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity(); //Exit all activity
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        //Alert Run Part
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }


    //            -------------------- [ Exit Msg Part End ] --------------------

    //----------------------------------------------------------------------------------------------


}