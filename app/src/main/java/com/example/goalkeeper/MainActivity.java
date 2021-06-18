package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Overview:
    // This activity is the point of entry for the app. When it is loaded, it initializes the app,
    // binds the IO service (which starts the underlying logic for the rest of the app), and
    // displays the title to the user, as well as two buttons, asking whether the user wants to go to goals,
    // or go to the schedule

    // OnCreate needs to:
    // 1.) Bind io Service
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // To do:
    //  1.) User Data function
    //  2.) Check IO Service state
    //  3.) onClick for Goals button starts app in Goals mode
    //  4.) onClick for schedule mode starts app in Schedules mode, reads from IO base settings, and starts default screen
}