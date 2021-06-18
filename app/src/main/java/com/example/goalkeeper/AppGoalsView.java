package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppGoalsView extends AppCompatActivity {

    // Overview:
    // This activity should take a selected goal and give information about it, including length,
    // due date, parent, and child goals, and have links to load this activity with their info. It
    // should also allow for minor edits to that goal specifically.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_view_goal);
    }
}