package com.example.goalkeeper;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.MissingResourceException;

// Overview:
// PlannerService is an abstraction service to provide backend service to the planner portion
// of the app.
public class AppPlannerService extends Service {
    // Generic Messenger variables
    MyIncomingHandler myIncomingHandler = new MyIncomingHandler();
    Messenger request = new Messenger(myIncomingHandler);
    Messenger response;

    // Messenger variables for IO
    boolean isBoundIO = false;
    Messenger requestForIO;
    Messenger replyFromIO = new Messenger(myIncomingHandler);
    ServiceConnection ioServiceConnection = new IOServiceConnection();

    // Variables for activities
    SharedPreferences preferences;
    Bundle sharedSettings;
    public AppPlannerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        init();
        return request.getBinder();
    }

    @Override
    public void onCreate(){
        super.onCreate();

        init();
        Log.i("AppPlannerService", "onCreate Entered");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        unBindIO();
    }

    protected void getSharedSettings(){
        if(preferences.contains("planner_default")){
            sharedSettings.putInt("planner_default", preferences.getInt("planner_default", 999));
        }
        if(preferences.contains("week_default")){
            sharedSettings.putInt("week_default", preferences.getInt("week_default", 999));
        }
        if(preferences.contains("notification_default")){
            sharedSettings.putInt("notification_default", preferences.getInt("notification_default", 999));
        }
    }

    protected boolean bindIO(){
        Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        return bindService(intentIO, ioServiceConnection, Context.BIND_AUTO_CREATE);

    }

    protected void unBindIO(){
        if(isBoundIO){
            unbindService(ioServiceConnection);
            isBoundIO = false;
        }
    }

    protected void init(){
        sharedSettings = new Bundle();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        getSharedSettings();
        Log.i("appPlannerService", "Init entered");
        // Bind IO
        try{
            isBoundIO = bindIO();

            Log.i("appPlannerService", "IO Service bound status: " + isBoundIO);

            if(!isBoundIO){
                throw new MissingResourceException("IO Service failed to bind", "MainActivity.class", "bad_bind");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }
    }

    protected void callDefaultActivity(){
        Log.i("appPlannerService", "callDefaultActivity entered");
        Intent intent;
        switch (sharedSettings.getInt("planner_default")){
            case 0:
                intent = new Intent(this, AppDay.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("appPlannerService", "appDay launched");
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, AppWeek.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("appPlannerService", "appWeek launched");
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, AppMonth.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("appPlannerService", "appMonth launched");
                startActivity(intent);
                break;
            default:
                throw new MissingResourceException("Settings not loaded", "AppPlannerService,class", "no_settings");
        }
    }

    public void requestForDefaults(){
        if(isBoundIO)
        {


            Bundle data = new Bundle();
            data.putInt("source", AppUtils.APP_PLANNER_SERVICE);
            data.putInt("request", AppUtils.READ_REQUEST);

            Message message = Message.obtain();
            message.replyTo = replyFromIO;
            message.setData(data);
            try {
                requestForIO.send(message);
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
        }
    }

    // Messaging handlers
    class MyIncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            response = msg.replyTo;
            Bundle payload = msg.getData();
            // Do some stuff with the variables and the reply codes

            Log.i("appPlannerService", "Message received");
            switch (payload.getInt("source")){
                case AppUtils.APP_IO:
                    switch(payload.getInt("reply")){
                        case AppUtils.READ_OK_RESULT_INCLUDED:
                            Log.i("appPlannerService", "Settings received");
                            getSharedSettings();
                            break;

                        case AppUtils.READ_BAD_NO_DATA: // Settings not found
                            Toast.makeText(getApplicationContext(), "Settings database not found",Toast.LENGTH_SHORT).show();
                            break;

                        case AppUtils.DB_WRITE_OK: // Database updated
                            Toast.makeText(getApplicationContext(), "Settings updated", Toast.LENGTH_SHORT).show();
                            break;

                        case AppUtils.DB_WRITE_FAILED: // Database update failed
                            Toast.makeText(getApplicationContext(), "Settings failed to update", Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            break;
                    }
                case AppUtils.APP_DAY:
                case AppUtils.APP_WEEK:
                case AppUtils.APP_MONTH:
                case AppUtils.MAIN_ACTIVITY:
                    switch (payload.getInt("request")){
                        case AppUtils.LAUNCH_ACTIVITY_REQUEST:
                            callDefaultActivity();
                            break;
                        default:
                            break;

                    }
                default:
                    break;
            }


        }
    }

    class IOServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            requestForIO = new Messenger(service);
            requestForDefaults();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            requestForIO = null;
        }
    }

}