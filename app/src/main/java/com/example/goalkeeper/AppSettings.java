 package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

 public class AppSettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
        // Bundles to hold existing and new settings

        Bundle existSettings, newSettings;

        // View handlers
        Spinner plannerSpinner, weekSpinner, notificationSpinner;

        // Arrays for spinner options

        String[] plannerOptions         = {"Day", "Week", "Month"};
        String[] weekOptions            = {"3 day", "5 day", "7 day"};
        String[] notificationOptions    = {"Gentle", "Normal", "Aggressive"};

        // Array adapters

        ArrayAdapter plannerAdapter, weekAdapter, notificationAdapter;

        // Overview:
        // This activity's purpose is to give the user a place to customize the behavior of the app.
        // It gains the current user settings from IO, and posts updated settings to IO to update the
        // database.

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_app_settings);

            plannerSpinner      = (Spinner) findViewById(R.id.SA_PlannerSpinner);
            weekSpinner         = (Spinner) findViewById(R.id.SA_WeekSpinner);
            notificationSpinner = (Spinner) findViewById(R.id.SA_NotificationSpinner);

            plannerSpinner.setOnItemSelectedListener(this);
            weekSpinner.setOnItemSelectedListener(this);
            notificationSpinner.setOnItemSelectedListener(this);

            plannerAdapter      = new ArrayAdapter(this, android.R.layout.simple_spinner_item, plannerOptions);
            weekAdapter         = new ArrayAdapter(this, android.R.layout.simple_spinner_item, weekOptions);
            notificationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, notificationOptions);

            plannerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            notificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            plannerSpinner.setAdapter(plannerAdapter);
            weekSpinner.setAdapter(weekAdapter);
            notificationSpinner.setAdapter(notificationAdapter);

            // Set default values for the spinners.
            // recode this to take values from existSettings
            plannerSpinner.setSelection(0);
            weekSpinner.setSelection(2);
            notificationSpinner.setSelection(1);
        }

         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }

         public void onClickSubmit(View view) {
         }

     // Settings List:
        //               [ <key>                | <default value>                ]
        //               [ plannerModeDefault   | day                            ]
        //                  * What activity the app starts the planner in
        //                  * Values: day, week, month
        //               [ weekModeDefault      | 7 day                          ]
        //                  * What mode week activity loads in
        //                  * Values: 3, 5, and 7 day
        //               [ notificationUrgency  | normal                         ]
        //                  * How agressive the notifications should be
        //                  * Values:  Gentle - on screen notification
        //                  *          Normal - on screen notification, vibration, wake from sleep
        //                  *          Aggressive - all above, plus interrupts do not disturb and sets
        //                  *                      an alarm
        //

        // To do:
        //  1.) Get settings from IO
        //  2.) onClick for submit button view bundles up current settings and sends them to IO if valid
        //  3.) Create private boolean validation function to ensure all new values are valid selections
        //  4.) onClick for Resync button sends command to reconnect to google calendar
        //  5.) onClick for Disconnect button view sends disconnect command

}