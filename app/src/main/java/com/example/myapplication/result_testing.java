package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class result_testing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_testing);
    }



    public void exit(View view) {
        Intent intent = new Intent(result_testing.this, MainActivity.class);
        startActivity(intent);
    }
}