package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppWeek extends AppCompatActivity {
    //  Overview:
    //  This activity should serve as an overview of the coming days and their schedule

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