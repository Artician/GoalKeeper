package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // OnCreate needs to:
    // 1.) Bind io Service
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // To do:
    // 1.) User Data function
    // 2.) Check IO Service state
    // 3.) Start main page when IO updates the variable
}