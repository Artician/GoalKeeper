package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppGoalsYear extends AppCompatActivity {

    // Overview:
    // Displays a list of goals with tasks due in a given year.
    // Displays each month, with goals with tasks due each month.
    // Will also display goals in danger of slipping.
    //
    // Definitions:
    // Slipping: A goal has fallen below a completion milestone. Slipping goals can be
    // reworked if the shortfall is small, but at >50% completion milestone, the goal must be
    // abandoned or restarted
    //
    // Color Code:
    // Gold - Completed
    // Green - On track
    // Blue - On track, has tasks due today
    // Red - Slipping - 75% - 50% of milestone goal
    // Purple - Dead - <50% milestone completion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_goals_year);
    }
}