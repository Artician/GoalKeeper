package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppSettings extends AppCompatActivity {

    // Overview:
    // This activity's purpose is to give the user a place to customize the behavior of the app.
    // It gains the current user settings from IO, and posts updated settings to IO to update the
    // database.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
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
    //                  *          Agressive - all above, plus interrupts do not disturb and sets
    //                  *                      an alarm
    //

    // To do:
    //  1.) Get settings from IO
    //  2.) onClick for submit button view bundles up current settings and sends them to IO if valid
    //  3.) Create private boolean validation function to ensure all new values are valid selections
    //  4.) onClick for Resync button sends command to reconnect to google calendar
    //  5.) onClick for Disconnect button view sends disconnect command

}