package com.example.thagavaltimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText title, category, description, purl;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title = (EditText)findViewById(R.id.txtTitle);
        category = (EditText)findViewById(R.id.txtCategory);
        description = (EditText)findViewById(R.id.txtDescription);
        purl = (EditText)findViewById(R.id.txtPurl);

        btnAdd = (Button)findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                clearAll();
            }
        });

    }

    private void insertData() {
        Map<String,Object> map = new HashMap<>();
        map.put("title",title.getText().toString());
        map.put("category",category.getText().toString());
        map.put("description",description.getText().toString());
        map.put("purl",purl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("posts").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Insert Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error while insert", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll(){
        title.setText("");
        category.setText("");
        description.setText("");
        purl.setText("");
    }

    //Back Button Concept
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}