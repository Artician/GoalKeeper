package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
    }

    // To do:
    //  1.) Get settings from IO
    //  2.) onClick for submit button view bundles up current settings and sends them to IO if valid
    //  3.) Create private boolean validation function to ensure all new values are valid selections
    //  4.) onClick for Resync button sends command to reconnect to google calendar
    //  5.) onClick for Disconnect button view sends disconnect command

}