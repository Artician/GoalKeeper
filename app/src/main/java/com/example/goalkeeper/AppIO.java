package com.example.goalkeeper;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class AppIO extends Service {
    // Messenger variables
    MyIncomingHandler myIncomingHandler = new MyIncomingHandler();
    Messenger request = new Messenger(myIncomingHandler);
    Messenger response;

    // Database variables
    AppSettingsDB settingsDB;
    AppSettingsDAO settingsDAO;
    AppEventsDB eventsDB;
    AppEventsDAO eventsDAO;

    // Data variables for other activities
    ArrayList<AppEventsEntity> events = new ArrayList<>();
    Bundle settingsObject = new Bundle();

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

    // Lifecycle Methods
    @Override
    public void onCreate(){
        initDB();
        new readFromEventsDBTask().execute();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return request.getBinder();
    }

    // Database Init  Helper functions
    protected void initDB(){
        settingsDB = Room.databaseBuilder(getApplicationContext(), AppSettingsDB.class, "settings-db").build();
        settingsDAO = settingsDB.appSettingsDAO();
        eventsDB = Room.databaseBuilder(getApplicationContext(), AppEventsDB.class, "events_db").build();
        eventsDAO = eventsDB.appEventsDAO();
    }

    // Settings DB helper functions
    protected boolean initDatabase(){
        // Initialize defaults
        Log.i("initDatabase", "initDatabase entered. SettingsDAO size is: " + settingsDAO.getAllSettings().size());
        if (settingsDAO.getAllSettings().size() < 3){
            Log.i("initDatabase", "Size less than 3, creating new database");
            AppSettingsEntity temp = new AppSettingsEntity();
            ArrayList<AppSettingsEntity> defaults = new ArrayList<>();
            // Eventually, this should get data from an initialization activity
            temp.setting_name  = "planner_default";
            temp.value         = 0;
            defaults.add(temp);

            temp = new AppSettingsEntity();
            temp.setting_name  = "week_default";
            temp.value         = 2;
            defaults.add(temp);


            temp = new AppSettingsEntity();
            temp.setting_name  = "notification_default";
            temp.value         = 1;
            defaults.add(temp);

            for(int i = 0; i < defaults.size(); i++){
                settingsDAO.insertEntry(defaults.get(i));
            }

            Log.i("initDatabase", "Operation complete, SettingsDAO Size is: " + settingsDAO.getAllSettings().size());
            return settingsDAO.getAllSettings().size() == 3;
        } else{
            return true;
        }
    }
    protected Bundle getSettings(){
        List<AppSettingsEntity> settingsList = settingsDAO.getAllSettings();
        Bundle retValue = new Bundle();

        for(int i = 0; i < settingsList.size(); i++){
            retValue.putInt(settingsList.get(i).setting_name, settingsList.get(i).value);
        }

        settingsObject = retValue;

        return retValue;
    }
    protected boolean updateSettings(Bundle settings){
        List<AppSettingsEntity> updateList = new ArrayList<>();
        int result;
        Log.i("updateSettings", "updateSettings entered, SettingsDAO size is: " + settingsDAO.getAllSettings().size());
        if(!settings.isEmpty()){
                boolean initialized = initDatabase();
                if(initialized){
                    if((settings.getInt("planner_default", 999) != 999)){
                        AppSettingsEntity temp = new AppSettingsEntity();
                        temp.setting_name   = "planner_default";
                        temp.value          = settings.getInt("planner_default");

                        updateList.add(temp);
                    }
                    if((settings.getInt("week_default", 999) != 999)){
                        AppSettingsEntity temp = new AppSettingsEntity();
                        temp.setting_name   = "week_default";
                        temp.value          = settings.getInt("week_default");

                        updateList.add(temp);
                    }
                    if((settings.getInt("notification_default", 999) != 999)){
                        AppSettingsEntity temp = new AppSettingsEntity();
                        temp.setting_name   = "notification_default";
                        temp.value          = settings.getInt("notification_default");

                        updateList.add(temp);
                    }

                    result = settingsDAO.updateSettings(updateList);


                    return result == updateList.size();
                }
                else{
                    return false;
                }

        }
        else
            return false;

    }

    // Events DB helper functions
    protected void getEvents(){
        List<AppEventsEntity> eventsList = eventsDAO.loadAllEvents();

        for(int i = 0; i < events.size(); i++){
            events.add(eventsList.get(i));
        }
    }
    protected void writeEvent(AppEventsEntity event, char code){
        switch(code){
            case 'I':
                insertEvent(event);
                break;
            case 'U':
                updateEvent(event);
                break;
            case 'D':
                deleteEvent(event);
                break;
            default:
                break;
        }
    }
    protected void insertEvent(AppEventsEntity newEvent){
        eventsDAO.insertEvent(newEvent);
    }
    protected void updateEvent(AppEventsEntity updateEvent){
        eventsDAO.updateEvent(updateEvent);
    }
    protected void deleteEvent(AppEventsEntity deleteEvent){
        eventsDAO.deleteEvent(deleteEvent);
    }
    protected Bundle makeEventBundle(){
        Bundle returnValue = new Bundle();
        Bundle temp = new Bundle();

        returnValue.putInt("size", events.size());

        for(int i = 0; i < events.size(); i++){
            temp.putInt("eventID", events.get(i).eventID);
            temp.putString("calendar", events.get(i).calendar);
            temp.putString("eventTitle", events.get(i).eventTitle);
            temp.putInt("startTime", events.get(i).startTime);
            temp.putInt("endTime", events.get(i).endTime);
            temp.putString("RRule", events.get(i).RRule);
            temp.putBoolean("hasParent", events.get(i).hasParent);
            temp.putInt("parentID", events.get(i).parentID);
            temp.putString("status", events.get(i).status);
            temp.putString("location", events.get(i).location);
            temp.putString("summary", events.get(i).summary);
            temp.putBoolean("doRemind", events.get(i).doRemind);
            temp.putInt("remindInterval", events.get(i).remindInterval);

            returnValue.putBundle("" + i, temp);
        }

        return returnValue;
    }

    // Messaging handlers
    class MyIncomingHandler extends Handler{
        @Override
        public void handleMessage(Message incomingMessage){
            response = incomingMessage.replyTo;
            Bundle payload = incomingMessage.getData();

            switch(payload.getInt("source")){
                case R.integer.MAIN_ACTIVITY:
                    replyToMainActivity(response, payload);
                    break;

                case R.integer.APP_SETTINGS:
                    switch (payload.getInt("request")){
                        case R.integer.READ_REQUEST: // Read from settings DB
                            new readFromSettingsDatabaseTask().execute(response);
                            break;
                        case R.integer.WRITE_REQUEST: // Write to settings DB
                            Envelope forTask = new Envelope(response, payload);
                            new writeToSettingsDatabaseTask().execute(forTask);
                            break;
                        default:
                            break;
                    }
                case R.integer.APP_PLANNER_SERVICE:
                    switch (payload.getInt("request")){
                        case R.integer.READ_REQUEST: // Read from settings DB
                            new readFromSettingsDatabaseTask().execute(response);
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }


        }
    }

    // Specific messaging functions
    protected void replyToMainActivity(Messenger postmarkedEnvelope, Bundle receivedData)  {
        if(receivedData.getInt("request") == R.integer.ACK_REQUEST){
            Message response = Message.obtain();
            Bundle sendData = new Bundle();
            sendData.putInt("source", R.integer.APP_IO);
            sendData.putInt("reply", R.integer.ACK);
            sendData.putString("reply_text", "OK");
            sendData.putBundle("settings", settingsObject);
            response.setData(sendData);

            try{
                postmarkedEnvelope.send(response);
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }
    protected void sendSettingsFromDB(Messenger postmarkedEnvelope, Bundle settings){
        Message response = Message.obtain();
        Bundle sendData = new Bundle();
        sendData.putInt("source", R.integer.APP_IO);

        if(settings.isEmpty()){
            sendData.putInt("reply", R.integer.READ_BAD_NO_DATA);

        } else{
            sendData.putInt("reply", R.integer.READ_OK_RESULT_INCLUDED);
            sendData.putBundle("settings", settings);
        }

        response.setData(sendData);

        try {
            postmarkedEnvelope.send(response);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }
    protected void sendDBUpdateStatus(Messenger postmarkedEnvelope, boolean success){
        Message response = Message.obtain();
        Bundle sendData = new Bundle();
        sendData.putInt("source", R.integer.APP_IO);

        if(success){
            sendData.putInt("reply", R.integer.DB_WRITE_OK);

        } else{
            sendData.putInt("reply", R.integer.DB_WRITE_FAILED);
        }

        response.setData(sendData);

        try {
            postmarkedEnvelope.send(response);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }


    // Custom data types
    private class Envelope{
        public Envelope() {
        }

        public Envelope(Messenger destination, Bundle data) {
            this.destination = destination;
            this.data = data;
        }

        public Messenger getDestination() {
            return destination;
        }

        public void setDestination(Messenger destination) {
            this.destination = destination;
        }

        public Bundle getData() {
            return data;
        }

        public void setData(Bundle data) {
            this.data = data;
        }

        public Messenger destination;
        public Bundle data;
    }
    private class DataPackage{
        public DataPackage(char code, AppEventsEntity data) {
            this.code = code;
            this.data = data;
        }

        public char getCode() {
            return code;
        }

        public void setCode(char code) {
            this.code = code;
        }

        public AppEventsEntity getData() {
            return data;
        }

        public void setData(AppEventsEntity data) {
            this.data = data;
        }

        public char code;
        public AppEventsEntity data;
    }

    // AsyncTask classes for settings database access
    private class readFromSettingsDatabaseTask extends AsyncTask<Messenger, Void, Void>{
        Bundle settings = new Bundle();
        Messenger response;

        @Override
        protected Void doInBackground(Messenger... messengers) {
            response = messengers[0];
            settings = getSettings();

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            sendSettingsFromDB(response, settings);
        }
    }
    private class writeToSettingsDatabaseTask extends AsyncTask<Envelope, Void, Void>{
        boolean success;
        Messenger destination;

        @Override
        protected Void doInBackground(Envelope... envelopes) {
            Envelope data = envelopes[0];
            destination = data.destination;
            //sendDBUpdateStatus(data.destination, updateSettings(data.data));
            success = updateSettings(data.data);
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            sendDBUpdateStatus(destination, success);
        }
    }

    // AsyncTask Classes for Events DB
    private class readFromEventsDBTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getEvents();
            return null;
        }

    }
    private class writeToEventsDBTask  extends AsyncTask<DataPackage, Void, Void>{

        @Override
        protected Void doInBackground(DataPackage... dataPackages) {
            DataPackage info = new DataPackage(dataPackages[0].code, dataPackages[0].data);

            writeEvent(info.data, info.code);
            return null;
        }
    }

    // Message structure:
    // [<key>               |<value>                    ]
    // [source              |calling activity ID code   ]
    // [request             |request code               ]
    //
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