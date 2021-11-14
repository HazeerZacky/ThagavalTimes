package com.example.thagavaltimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class Login extends AppCompatActivity {

    //Initialize Admob Banner Ad
    private AdView adView;

    //Initialize Admob interstitial Ad
    private InterstitialAd mInterstitialAd ;


    ///Initialize Variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assign Variable
        drawerLayout = findViewById(R.id.drawerLayout);


        //Admob Banner ad Start
        adView = findViewById(R.id.home_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }

    public void ClickLiveNews(View view){
        MainActivity.redirectActivity(this,LiveNews.class);
    }

    public void ClickCoronaLiveTicker(View view){
        MainActivity.redirectActivity(this,CoronaLiveTicker.class);
    }

    public void ClickCategory(View view){
        MainActivity.redirectActivity(this,Category.class);
    }

    public void ClickInstagram(View view){
        MainActivity.redirectActivity(this,Instagram.class);
    }

    public void ClickSettings(View view){
        MainActivity.redirectActivity(this,Settings.class);
    }

    public void ClickSupport(View view){
        MainActivity.redirectActivity(this,Support.class);
    }
    //
    public void ClickAboutUs(View view){
        MainActivity.redirectActivity(this,AboutUs.class);
    }

    public void ClickLogin(View view){
        recreate();
    }

    //Back Button Concept
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this,MainActivity.class));
    }
}