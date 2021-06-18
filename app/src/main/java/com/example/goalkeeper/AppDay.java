package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppDay extends AppCompatActivity {

    // Overview:
    // This activity's main purpose is to display a detailed view of all goals and events the user
    // has scheduled for that day. It also should have a shortcut button for the "daily" section of
    // the goals menu. Current version will show a recycler view with all current events, or an
    // empty list with the option to create a new event.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_day);
    }

    // To do:
    //  1.) onClick for floating add button calls NewEvent with no settings
    //  2.) onClick for an event calls NewEvent activity with settings for that event
    //  3.) onClick for goalButton calls DayGoalView
}