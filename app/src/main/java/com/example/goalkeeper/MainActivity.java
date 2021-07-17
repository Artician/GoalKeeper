package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.MissingResourceException;

public class MainActivity extends AppCompatActivity {
    // Messaging and connection variables
    MyIncomingHandler incomingHandler = new MyIncomingHandler();
    boolean isBoundIO = false;
    boolean isBoundPlanner = false;
    Messenger requestForIO;
    Messenger replyFromIO = new Messenger(incomingHandler);
    Messenger requestForPlanner;
    Messenger replyFromPlanner;
    ServiceConnection ioServiceConnection = new IOServiceConnection();
    ServiceConnection plannerServiceConnection = new PlannerServiceConnection();

    // Views
    TextView debugText;

    // Variables

    Bundle settings = new Bundle();

    // Overview:
    // This activity is the point of entry for the app. When it is loaded, it initializes the app,
    // binds the IO service (which starts the underlying logic for the rest of the app), and
    // displays the title to the user, as well as two buttons, asking whether the user wants to go to goals,
    // or go to the schedule

    // OnCreate needs to:
    // 1.) Bind io Service
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Start IO service
        //Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        //startService(intentIO);

        try{
            if(!init()){
                throw new MissingResourceException("Application components failed to load.", "MainActivity.class", "bad_init");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        try{
            if(!init()){
                throw new MissingResourceException("Application components failed to load.", "MainActivity.class", "bad_init");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        try{
            if(!init()){
                throw new MissingResourceException("Application components failed to load.", "MainActivity.class", "bad_init");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unBindIO();
        unBindPlanner();
    }

    @Override
    protected void onPause(){
        super.onPause();
        unBindIO();
        unBindPlanner();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unBindIO();
        unBindPlanner();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.HA_settings_action:
                callSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean init(){
       // This function will always return true unless it throws an exception.

        // Set views

        debugText = (TextView) findViewById(R.id.MA_DebugText);

        setDebugText("IO Service status:     .     .     .");

        // Start Services

        try{
            startIO();
        } catch (MissingResourceException e){
            e.printStackTrace();
        }

        // Bind Services

        try{
            isBoundIO = bindIO();

            if(!isBoundIO){
                throw new MissingResourceException("IO Service failed to bind", "MainActivity.class", "bad_bind");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }
        // Send message to the service

        return true;
    }

    protected void initPlanner(){

        try{
            isBoundPlanner = bindPlanner();
            if(!isBoundPlanner){
              throw new MissingResourceException("Planner service failed to bind", "MainActivity.class", "bad_bind");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }
    }

    public void messageToIO(){
        if(isBoundIO)
        {


            Bundle data = new Bundle();
            data.putInt("source", R.integer.MAIN_ACTIVITY);
            data.putInt("request", R.integer.ACK_REQUEST);

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

    public void messageToPlanner(){
        if(isBoundPlanner)
        {
            Bundle data = new Bundle();
            data.putInt("source", R.integer.MAIN_ACTIVITY);
            data.putInt("request", R.integer.LAUNCH_ACTIVITY_REQUEST);

            Message message = Message.obtain();
            message.replyTo = replyFromPlanner;
            message.setData(data);
            try {
                requestForPlanner.send(message);
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
        }
    }

    protected void startIO() throws MissingResourceException{
        ComponentName resValue;
        Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        resValue = startService(intentIO);

        if(resValue == null)
            throw new MissingResourceException("Unable to start service", "MainActivity.class", "bad_start");
    }

    protected boolean bindIO(){
        Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        return bindService(intentIO, ioServiceConnection, Context.BIND_AUTO_CREATE);

    }

    protected void startPlanner() throws MissingResourceException{
        ComponentName resValue;
        Intent intentPlanner = new Intent(getApplicationContext(), AppPlannerService.class);
        resValue = startService(intentPlanner);

        if(resValue == null)
            throw new MissingResourceException("Unable to start service", "MainActivity.class", "bad_start");
    }

    protected boolean bindPlanner() throws MissingResourceException{
        Intent intentPlanner = new Intent(getApplicationContext(), AppPlannerService.class);
        return bindService(intentPlanner, plannerServiceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void unBindIO(){
        if(isBoundIO){
            unbindService(ioServiceConnection);
            isBoundIO = false;
        }
    }

    protected void unBindPlanner(){
        if(isBoundPlanner){
            unbindService(plannerServiceConnection);
            isBoundPlanner = false;
        }
    }

    protected void setDebugText(CharSequence text){
        debugText.setText(text);
    }

    protected void callSettings(){
        Intent intent = new Intent(getApplicationContext(), AppSettings.class);
        startActivity(intent);
    }

    public void onClickPlannerButton(View view) {
         initPlanner();
    }

    protected void callPlanner(int option){

        Intent intent;
        switch (option){
            case 0:
                intent = new Intent(getApplicationContext(), AppDay.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getApplicationContext(), AppWeek.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), AppMonth.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void onClickGoalsButton(View view) {
    }


    class MyIncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            int replyCode;
            CharSequence replyText;
            Bundle payload = msg.getData();
            // Do some stuff with the variables and the reply codes

            replyCode   = payload.getInt("reply");
            replyText   = payload.getCharSequence("reply_text");
            settings    = payload.getBundle("settings");

            if(replyCode == R.integer.ACK){ // ACK code from IO
                String newDebug = "IO Service status:     " +
                        replyText;
                setDebugText(newDebug);
            }
        }
    }

    class IOServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            requestForIO = new Messenger(service);
            messageToIO();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            requestForIO = null;
        }
    }

    class PlannerServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            requestForPlanner = new Messenger(service);
            messageToPlanner();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            requestForPlanner = null;
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
    // 201 -
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
    // 318 - AppPlannerService

    // To do:
    //  1.) User Data function
    //  2.) Check IO Service state
    //  3.) onClick for Goals button starts app in Goals mode
    //  4.) onClick for schedule mode starts app in Schedules mode, reads from IO base settings, and starts default screen
}