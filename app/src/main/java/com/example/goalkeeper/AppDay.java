package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppDay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_day);
    }

    // To do:
    //  1.) onClick for floating add button calls NewEvent with no settings
    //  2.) onClick for an event calls NewEvent activity with settings for that event
}