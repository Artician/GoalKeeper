package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppGoalsBuilder extends AppCompatActivity {

    // Overview:
    // This should be the main point for building a goal. The answer to each question should add
    // specific follow-ups. This structure will be detailed in comments below

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_goals_builder);
    }
}

/*
    Type of goal
    Setting goals you plan to stick to can be a long, involved process. Make sure you have time to
    give each step the right amount of thought before starting.
    --Long (6 Months+)
    --Medium (1 - 6 Months)
    --Short (1 day to 1 month)

    If Long
        Long term goals are collections of medium-term goals
        --Prompt user for 2+ medium term goals
            Once these are set, iterate down the list for each.
        -- Once all goals below are set, ask for a reasonable timeframe
            -Optionally, suggest a timeframe based on timeframes of child activities
        -- Check for any overlapping short term goals between mid term goals and ask if they
           should be merged. Merging goals simply means completing a child goal will update all
           parent goals.

    If Medium
        Medium term goals are collections of many short term goals. Think if a series of steps
        to accomplish this goal that will each take between one day and one week to accomplish.
        --Prompt user for 2+ short term goals
            Iterate through each to set all short term goals related to each task


    If Short
        Short term goals are goals that can be accomplished in a day or a week. This app divides
        the two to help keep you on track with your goals and make real progress.
        These can also be something you want to be sure you do each day or each week.
            --Prompt for the user to put in a due date/time
            --If no parent, ask if the goal should repeat, and when

    Goals will display with a % completed based on child tasks (apart from repeating short goals)
    Repeating short goals will show a average completion rate for each daily/weekly goal

    Goals will generate events and notifications according to the following rules:
    Repeating goals only:
        * Generate a notification and calendar event for 5 minutes before due time (can be changed)

    All other goals:
        * Short term goals should have a calendar event associated with the due time, and a
          notification that is set for 5 minutes before due time (can be changed)
        * Medium goals should generate a weekly notification to keep user on track, and a monthly
          progress evaluation
        * Long term goals should generate a monthly notification to keep user on track, and a
          quarterly progress evaluation

    Progress evaluations:
        Periodically, longer goals should have mandatory progress evaluations. These are to help
        keep the user on track. Skipping a progress evaluation will cancel the goal and any parents,
        though there will be an option to keep all child goals (to keep up good habits). These will
        be further defined in their own file

    Goal class structure:

        data members:
        - int           Type (0 for repeating, 1, for short, 2 for medium, 3 for long)
        - float         Completion
        - float         Score
        - Goal          Parent
        - List<Goal>    Child
        - String        Title
        - String        Description
        - Date          DueDate
        - Boolean       Active
        - Boolean       Complete

        functions:
        + Goal(int Type, String Name, Date DueDate)
        + void setParent(Goal Parent)
        + void addChild(Goal Child)
        - Setters and Getters
        --To be continued
 */