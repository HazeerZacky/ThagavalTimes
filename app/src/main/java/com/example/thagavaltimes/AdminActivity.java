package com.example.thagavaltimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {


     RecyclerView recyclerView1;
     AdminMainAdapter adminMainAdapter;

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
}