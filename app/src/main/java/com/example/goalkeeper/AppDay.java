package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AppDay extends AppCompatActivity {
    TextView displayDate, displayDay, displayMonth, displayYear;

    // Overview:
    // This activity's main purpose is to display a detailed view of all goals and events the user
    // has scheduled for that day. It also should have a shortcut button for the "daily" section of
    // the goals menu. Current version will show a recycler view with all current events, or an
    // empty list with the option to create a new event.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_day);
        displayDate  = (TextView) findViewById(R.id.DA_dateText);
        displayDay   = (TextView) findViewById(R.id.DA_dayText);
        displayMonth = (TextView) findViewById(R.id.DA_monthText);
        displayYear  = (TextView) findViewById(R.id.DA_yearText);
    }

    // Data manipulation

    protected void

    public void onClickDayGoal(View view) {
    }

    // To do:
    //  1.) onClick for floating add button calls NewEvent with no settings
    //  2.) onClick for an event calls NewEvent activity with settings for that event
    //  3.) onClick for goalButton calls DayGoalView
}