package com.example.thagavaltimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {


     RecyclerView recyclerView1;
     AdminMainAdapter adminMainAdapter;
     FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView1 = (RecyclerView)findViewById(R.id.arv);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("posts"), MainModel.class)
                        .build();

        adminMainAdapter = new AdminMainAdapter(options);
        recyclerView1.setAdapter(adminMainAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,AddActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adminMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adminMainAdapter.stopListening();
    }


    //Back Button Concept
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        startActivity(new Intent(AdminActivity.this,Login.class));
    }
}