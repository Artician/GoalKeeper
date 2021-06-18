package com.example.goalkeeper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppWebConnect extends Service {

    // Overview:
    // This service's purpose is to manage the connection to Google Calendar servers. It is
    // created and bound directly only by IO. It gains the account tokens from IO, and listens for
    // updates. When events are changed, it will push the changes to the server.

    public AppWebConnect() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // To do:
    //  1.) Create function to get account tokens from IO and open connection to google calendar
    //  2.) Create function to get current calendar
    //  3.) Create function to get current events
    //  4.) Create function to push calendar updates to web
    //  5.) Create function to push events updates to the web
    //  6.) Create function to update all
    //  7.) Create function to send calendar and events to IO
    //  8.) Create function to close google calendar connection
    //  9.) Create function to check current calendar from web and validate changes
}