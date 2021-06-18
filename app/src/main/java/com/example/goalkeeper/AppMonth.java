package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppMonth extends AppCompatActivity {

    // Overview:
    // This activity will display a monthly view of the calendar. Tapping on a week's row number
    // should call the Week view activity with user defaults, and tapping on a day should bring up
    // that day's Day activity. There should also be a shortcut for long-term goals.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_month);
    }



}