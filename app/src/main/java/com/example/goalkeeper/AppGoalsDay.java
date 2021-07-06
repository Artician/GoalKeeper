package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppGoalsDay extends AppCompatActivity {

    // Overview:
    // Shows all tasks due on a given day. The default is the current day, but can be called with
    // any date. Tasks are listed in order of due time, and grouped by parent activity. In case
    // of collision for tasks parent activity... There shouldn't be an issue, if I only shallow
    // copy. Mutex lock can also be a solution.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_goals_day);
    }
}