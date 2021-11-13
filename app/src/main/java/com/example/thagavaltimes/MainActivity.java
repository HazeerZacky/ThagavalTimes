package com.example.thagavaltimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    //Initialize variable
    DrawerLayout drawerLayout;

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    //FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation Part
        drawerLayout = findViewById(R.id.drawerLayout);

        //Database Part
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("posts"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

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

    //-------------------- [ Navigation Part End ] --------------------

}