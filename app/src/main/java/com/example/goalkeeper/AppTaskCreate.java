package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppTaskCreate extends AppCompatActivity {

    // Overview: This should be a fragment, but I'll have it be here for
    // now until I get some progress. This allows a user to create a new task, and updates IO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_task_create);
    }

    // To do:
    // 1.) Take information from the user to create a task.
    // 2.) Update IO with the updated task list for that goal.
}