package com.example.thagavaltimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.thagavaltimes.Models.NewsApiResponse;
import com.example.thagavaltimes.Models.NewsHeadlines;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.List;

public class LiveNews extends AppCompatActivity implements SelectListener, View.OnClickListener {

    Button b1,b2,b3,b4,b5,b6,b7;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    SearchView searchView;

    //Initialize Admob Banner Ad
    private AdView adView;

    //Initialize Admob interstitial Ad
    private InterstitialAd mInterstitialAd ;

    ///Initialize Variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_news);

        //Assign Variable
        drawerLayout = findViewById(R.id.drawerLayout);


        //Admob Banner ad Start
        adView = findViewById(R.id.home_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //Admob Interstitial ad Start
        loadInterstitialAd();

        //News
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fatching news articals...");
        dialog.show();

        //Scrol category
        b1 = findViewById(R.id.btn1);
        b1.setOnClickListener(this);

        b2 = findViewById(R.id.btn2);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.btn3);
        b3.setOnClickListener(this);

        b4 = findViewById(R.id.btn4);
        b4.setOnClickListener(this);

        b5 = findViewById(R.id.btn5);
        b5.setOnClickListener(this);

        b6 = findViewById(R.id.btn6);
        b6.setOnClickListener(this);

        b7 = findViewById(R.id.btn7);
        b7.setOnClickListener(this);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()){
                Toast.makeText(LiveNews.this, "No Data found", Toast.LENGTH_SHORT).show();
            }else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
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
        recreate();
    }

    public void ClickCoronaLiveTicker(View view){
        MainActivity.redirectActivity(this,CoronaLiveTicker.class);
        finish();
    }

    public void ClickCategory(View view){
        MainActivity.redirectActivity(this,Category.class);
        finish();
    }

    public void ClickSettings(View view){
        MainActivity.redirectActivity(this,Settings.class);
        finish();
    }

    public void ClickInstagram(View view){
        MainActivity.redirectActivity(this,Instagram.class);
        finish();
    }

    public void ClickSupport(View view){
        MainActivity.redirectActivity(this,Support.class);
        finish();
    }
    //
    public void ClickAboutUs(View view){
        MainActivity.redirectActivity(this,AboutUs.class);
        finish();
    }

    public void ClickLogin(View view){
        MainActivity.redirectActivity(this,Login.class);
        finish();
    }

    //Back Button Concept
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LiveNews.this,MainActivity.class));
        showAd();
    }

    //----------------------------------------------------------------------------------------------
    //---------------------------------- [ Interstitial Add End ] ----------------------------------

    public void showAd(){

        if (mInterstitialAd!=null){
            mInterstitialAd.show(LiveNews.this);
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

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news artical of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category, null);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(LiveNews.this, DetailsActivity.class)
                .putExtra("data", headlines));
    }
    //---------------------------------- [ Interstitial Add End ] ----------------------------------
    //----------------------------------------------------------------------------------------------
}