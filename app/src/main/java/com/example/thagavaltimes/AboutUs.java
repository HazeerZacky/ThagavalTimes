package com.example.thagavaltimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


import android.annotation.SuppressLint;
import android.os.Bundle;
//import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {

    //Initialize Admob Banner Ad
    private AdView adView;

    //Initialize Admob interstitial Ad
    private InterstitialAd mInterstitialAd ;

    ///Initialize Variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Assign Variable
        drawerLayout = findViewById(R.id.drawerLayout);


        //Admob Banner ad Start
        adView = findViewById(R.id.home_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //Admob Interstitial ad Start
        loadInterstitialAd();

        //About Page
        Element versionElement = new Element();
        versionElement.setTitle("Version 0.4");

        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");


        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.t1)
                .setDescription("\"கற்றதை கற்பி, பெற்றதை பகிரு\" \n Teach what you have learned. Share what you have received")
                .addItem(versionElement)
                .addGroup("CONNECT US!")
                .addEmail("contact.ttimes@gmail.com")
                .addInstagram("thagavaltimes")    //Your instagram id
                .addFacebook("ThagavalTimes")
                .addTwitter("thagavaltimes")
                .addPlayStore("com.example.yourprojectname")   //Replace all this with your package name
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }

    private Element createCopyright()
    {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by HAZKY EDITS", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        // copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUs.this,copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
        finish();
    }

    public void ClickLiveNews(View view){
        MainActivity.redirectActivity(this,LiveNews.class);
        finish();
    }

    public void ClickCoronaLiveTicker(View view){
        MainActivity.redirectActivity(this,CoronaLiveTicker.class);
        finish();
    }

    public void ClickCategory(View view){
        MainActivity.redirectActivity(this,Category.class);
        finish();
    }

    public void ClickInstagram(View view){
        MainActivity.redirectActivity(this,Instagram.class);
        finish();
    }

    public void ClickSettings(View view){
        MainActivity.redirectActivity(this,Settings.class);
        finish();
    }

    public void ClickSupport(View view){
        MainActivity.redirectActivity(this,Support.class);
        finish();
    }
    //
    public void ClickAboutUs(View view){
        recreate();
    }

    public void ClickLogin(View view){
        MainActivity.redirectActivity(this,Login.class);
        finish();
    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AboutUs.this,MainActivity.class));
        showAd();
    }



    //----------------------------------------------------------------------------------------------
    //---------------------------------- [ Interstitial Add End ] ----------------------------------

    public void showAd(){

        if (mInterstitialAd!=null){
            mInterstitialAd.show(AboutUs.this);
        }
        else {
            Log.d("Ad-test", "Ad faild to load");
        }
    }

    private void loadInterstitialAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.interstitial_ad_unit_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                Log.d("Ad-test", "Ad loadede successfully");
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("Ad-test", "Ad faild to show");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Log.d("Ad-test", "Ad showd successfully");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.d("Ad-test", "Ad Dismissed");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("Ad-test", "Ad faild to load");
                mInterstitialAd = null;
            }
        });
    }
    //---------------------------------- [ Interstitial Add End ] ----------------------------------
    //----------------------------------------------------------------------------------------------
}