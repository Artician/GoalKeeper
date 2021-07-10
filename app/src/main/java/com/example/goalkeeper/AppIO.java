package com.example.goalkeeper;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class AppIO extends Service {
    MyIncomingHandler myIncomingHandler = new MyIncomingHandler();

    Messenger request = new Messenger(myIncomingHandler);
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
        return request.getBinder();
    }

    public static boolean isRunning(){
        return isRunning;
    }

    class MyIncomingHandler extends Handler{
        @Override
        public void handleMessage(Message incomingMessage){
            response = incomingMessage.replyTo;
            Bundle payload = incomingMessage.getData();

            switch(payload.getInt("source")){
                case 301:
                    replyToMainActivity(response, payload);
                    break;
                default:
                    break;
            }


        }
    }

    // Specific replyTo methods to make code more readable

    protected void replyToMainActivity(Messenger postmarkedEnvelope, Bundle receivedData)  {
        if(receivedData.getInt("request") == 100){
            Message response = new Message();
            Bundle sendData = new Bundle();
            sendData.putInt("source", 302);
            sendData.putInt("reply", 200);
            sendData.putString("reply_text", "OK");
            response.setData(sendData);

            try{
                postmarkedEnvelope.send(response);
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }

    // Message structure:
    // [<key>               |<value>                    ]
    // [source              |calling activity ID code   ]
    // [request             |request code               ]
    //
    // Request codes:
    // 100 - Request for acknowledgement
    // 101 - Request to read data
    // 102 - Request to write data
    // 103 - Request for update to AppWebConnect
    // 104 - Request to update notifications
    //
    // Response codes:
    // 200 - ACK
    // 201 - NACK
    // 202 - Aff - data included
    // 203 - Neg - data not found
    // 204 - Aff - data not included
    // 205 - Neg - data not included
    // 206 - Successful write to database
    // 207 - Failed to write to database
    // 208 - Successful push to AppWebConnect
    // 209 - Failed push to AppWebConnect
    // 210 - Successful push to AppNotification
    // 211 - Failed push to AppNotification
    //
    // ID Codes:
    // 301 - MainActivity
    // 302 - AppIO
    // 303 - AppWebConnect
    // 304 - AppNotification
    // 305 - AppSettings
    // 306 - AppDay
    // 307 - AppWeek
    // 308 - AppMonth
    // 309 - AppGoal
    // 310 - AppGoalsDay
    // 311 - AppGoalsWeek
    // 312 - AppGoalsMonth
    // 313 - AppGoalsYear
    // 314 - AppGoalsMaster
    // 315 - AppGoalsBuilder
    // 316 - AppTaskView
    // 317 - AppTaskCreate
    // 318 -

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