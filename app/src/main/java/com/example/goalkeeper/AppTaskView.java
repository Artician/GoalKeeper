package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppTaskView extends AppCompatActivity {

    // Overview:
    // Tasks are the bottom level of goals. They are how goals are tracked, and how progress is
    // measured. The user will be able to create tasks, but each task, once created, can't be
    // deleted or changed, it can only be completed or failed.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_task_view);
    }

    // To do:
    // onClick of complete button completes the task.
    // onClick of cancel button fails the task
}