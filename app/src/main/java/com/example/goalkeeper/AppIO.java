package com.example.goalkeeper;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

public class AppIO extends Service {
    Messenger request;
    Messenger response;
    private static boolean isRunning = false;
    public AppIO() {
    }

    // Overview:
    // IO is the center of the entire application. It provides an abstraction layer between data and
    // activities, as well as a layer of abstraction between other services for validation. When
    // started, it will create and bind te WebConnect and Notification services, and be the source
    // of data for those services.

    // Things onBind has to do:
    // 1.) Link to User Info DB
    // 2.) Link to Events DB

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static boolean isRunning(){
        return isRunning;
    }

    class AppIncomingHandler extends Handler{
        @Override
        public void handleMessage(Message incomingMessage){
            response = incomingMessage.replyTo;


        }
    }

    // To do:
    //  1.) Access DAOs for User Info DB
    //  2.) Access DAOs for Events DB
    //  3.) Create boolean controlled function for data safe access to DAOs
    //  4.) Create boolean controlled functions to bind WebConnect Service
    //  5.) Create function for polling WebConnect for updates
    //  6.) Create a function for pushing updates to WebConnect
    //  7.) Create a boolean controlled function for binding Notification service
    //  8.) Create function for getting notification queue from Notification service -- Debug use only, remove before release
    //  9.) Create function for pushing new notifications to Notification service
    // 10.) Create function to read settings changes from the user
    // 11.) Create function to send current settings to settings activity
    // 12.) Create function to push updated settings to User Info DB
    // 13.) Create a function to push starting activity to main activity as well as event queue and relevant settings
    // 14.) Create function to receive new events and push them to WebConnect and Notification
    // 15.) Create a function to receive new goals and push them to Notification
    // 16.) Create a function to update and validate Events DB
    // 17.) Create a function to determine first use and call an introductory information gathering activity and populate info

}