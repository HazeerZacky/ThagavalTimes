package com.example.thagavaltimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoronaLiveTicker extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    CountryCodePicker countryCodePicker;
    TextView mtodaytotal, mtotal, mactive, mtodayactive, mrecovered, mtodayrecovered, mdeaths, mtodaydeaths;

    String country;
    TextView mfilter;
    Spinner spinner;
    String[] types= {"cases", "deaths", "recovered", "active"};
    private List<ModelClassCovid> modelClassList;
    private List<ModelClassCovid> modelClassList2;

    PieChart mpieChart;
    private RecyclerView recyclerView;
    com.example.thagavaltimes.AdapterCovid adapter;

    //Initialize Admob Banner Ad
    private AdView adView;

    //Initialize Admob interstitial Ad
    private InterstitialAd mInterstitialAd ;

    ///Initialize Variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_live_ticker);

        //Assign Variable
        drawerLayout = findViewById(R.id.drawerLayout);

        //Admob Banner ad Start
        adView = findViewById(R.id.home_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //Admob Interstitial ad Start
        loadInterstitialAd();

        //COVID
        countryCodePicker = findViewById(R.id.cpp);
        //mtodayactive = findViewById(R.id.todayactive);
        mactive = findViewById(R.id.totalactive);
        mdeaths = findViewById(R.id.totaldeath);
        mtodaydeaths = findViewById(R.id.todaydeath);
        mrecovered = findViewById(R.id.totalrecovered);
        mtodayrecovered = findViewById(R.id.todayrecovered);
        mtotal = findViewById(R.id.totalcase);
        mtodaytotal = findViewById(R.id.todaytotal);
        mpieChart = findViewById(R.id.piechart);
        spinner = findViewById(R.id.spinner);
        mfilter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerview);
        modelClassList = new ArrayList<>();
        modelClassList2 = new ArrayList<>();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        ApiUtilitiesCovid.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModelClassCovid>>() {
            @Override
            public void onResponse(Call<List<ModelClassCovid>> call, Response<List<ModelClassCovid>> response) {
                modelClassList2.addAll(response.body());
                // adapter.notify();

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelClassCovid>> call, Throwable t) {

            }
        });

        adapter = new AdapterCovid(getApplicationContext(),modelClassList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        countryCodePicker.setAutoDetectedCountry(true);
        country = countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchdata();
            }
        });
        fetchdata();
    }

    private void fetchdata() {
        ApiUtilitiesCovid.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModelClassCovid>>() {
            @Override
            public void onResponse(Call<List<ModelClassCovid>> call, Response<List<ModelClassCovid>> response) {
                modelClassList.addAll(response.body());
                for (int i=0; i<modelClassList.size(); i++){
                    if (modelClassList.get(i).getCountry().equals(country)){
                        mactive.setText((modelClassList.get(i).getActive()));
                        mtodaydeaths.setText((modelClassList.get(i).getTodayDeaths()));
                        mtodayrecovered.setText((modelClassList.get(i).getTodayRecovered()));
                        mtodaytotal.setText((modelClassList.get(i).getTodayCases()));
                        mtotal.setText((modelClassList.get(i).getCases()));
                        mdeaths.setText((modelClassList.get(i).getDeaths()));
                        mrecovered.setText((modelClassList.get(i).getRecovered()));

                        int active, total, recovered, deaths;

                        active = Integer.parseInt(modelClassList.get(i).getActive());
                        total = Integer.parseInt(modelClassList.get(i).getCases());
                        recovered = Integer.parseInt(modelClassList.get(i).getRecovered());
                        deaths = Integer.parseInt(modelClassList.get(i).getDeaths());

                        updategraph(active,total,recovered,deaths);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClassCovid>> call, Throwable t) {

            }
        });
    }

    private void updategraph(int active, int total, int recovered, int deaths) {

        mpieChart.clearChart();
        mpieChart.addPieSlice(new PieModel("Confirm",total, Color.parseColor("#FFB701")));
        mpieChart.addPieSlice(new PieModel("Active",active, Color.parseColor("#FF4CAF50")));
        mpieChart.addPieSlice(new PieModel("Recovered",recovered, Color.parseColor("#38ACCD")));
        mpieChart.addPieSlice(new PieModel("Deaths",deaths, Color.parseColor("#F55C47")));
        mpieChart.startAnimation();
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
        recreate();
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
        startActivity(new Intent(CoronaLiveTicker.this,MainActivity.class));
        showAd();
    }

    //----------------------------------------------------------------------------------------------
    //---------------------------------- [ Interstitial Add End ] ----------------------------------

    public void showAd(){

        if (mInterstitialAd!=null){
            mInterstitialAd.show(CoronaLiveTicker.this);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = types[i];
        mfilter.setText(item);
        adapter.filter(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //---------------------------------- [ Interstitial Add End ] ----------------------------------
    //----------------------------------------------------------------------------------------------
}