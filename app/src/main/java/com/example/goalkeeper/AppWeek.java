package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppWeek extends AppCompatActivity {
    //  Overview:
    //  This activity should serve as an overview of the coming days and their schedule. It will
    //  gain a variable from IO that determines the mode it loads in, either displaying:
    //  The current day, and the day before and after (3 day), the current M-F period (5 day) and
    //  the current full week (7 day). It should also have a button for weekly goals.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_week);
    }
    // To do:
    //  1.) onClick for goalButton brings up Weekly goals
    //  2.) onClick for a day brings up Day view
    //  3.) onClick for 3, 5, and 7 day variants should change between each view by destroying/recreating recyclerview items
    //  4.) onClick for each arrow button should
}