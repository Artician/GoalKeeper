package com.example.goalkeeper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppNotification extends Service {

    // Overview:
    // The Notification service should only communicate directly with IO, from which it gains
    // the list of current events and goals to display notifications for, as well as the user's
    // custom notification settings. It should then bundle that data into notifications, and then
    // display notifications to the user at the correct times.

    public AppNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // To do:
    //  1.) Create a function to get current event and goal queues and settings
    //  2.) Create notifications from current event and goal queues to push to the user based on settings
}