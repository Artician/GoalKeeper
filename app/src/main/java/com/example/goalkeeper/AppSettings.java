 package com.example.goalkeeper;

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
 import android.util.Log;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Spinner;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import java.util.MissingResourceException;

 // Structure notes:
 // onCreate sill be used to bind services, but onServiceConnected will update everything.

 public class AppSettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

     MyIncomingHandler incomingHandler = new MyIncomingHandler();
     boolean isBoundIO = false;
     Messenger requestForIO;
     Messenger replyFromIO = new Messenger(incomingHandler);
     ServiceConnection ioServiceConnection = new IOServiceConnection();

        // Bundles to hold existing and new settings

        Bundle existSettings, newSettings;

        // View handlers
        Spinner plannerSpinner, weekSpinner, notificationSpinner;

        // Arrays for spinner options

        String[] plannerOptions         = {"Day", "Week", "Month"};
        String[] weekOptions            = {"3 day", "5 day", "7 day"};
        String[] notificationOptions    = {"Gentle", "Normal", "Aggressive"};

        // Array adapters

        ArrayAdapter<String> plannerAdapter, weekAdapter, notificationAdapter;

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

            plannerAdapter      = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plannerOptions);
            weekAdapter         = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weekOptions);
            notificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notificationOptions);

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

            try{
                isBoundIO = bindIO();

                if(!isBoundIO){
                    throw new MissingResourceException("IO Service failed to bind", "AppSettings.class", "bad_bind");
                }
            } catch (MissingResourceException e){
                e.printStackTrace();
            }
        }

     @Override
     public boolean onCreateOptionsMenu(Menu menu){
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.action_bar_settings, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item){
         switch(item.getItemId()){
             case R.id.home_action:
                 callHome();
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
     }

     protected boolean bindIO(){
         Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
         return bindService(intentIO, ioServiceConnection, Context.BIND_AUTO_CREATE);

     }

     public void requestForDefaults(){
         if(isBoundIO)
         {


             Bundle data = new Bundle();
             data.putInt("source", 305);
             data.putInt("request", 101);

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

     public void updateDB(Bundle update){
            Bundle payload = new Bundle(); //Bundle.deepcopy isn't in this sdk
            payload.putInt("source", 305);
            payload.putInt("request", 102);
            payload.putInt("planner_default", update.getInt("planner_default"));
            payload.putInt("week_default", update.getInt("week_default"));
            payload.putInt("notification_default", update.getInt("notification_default"));

            Message message = Message.obtain();
            message.replyTo = replyFromIO;
            message.setData(payload);

            try{
                requestForIO.send(message);
            } catch (RemoteException e){
                e.printStackTrace();
            }
     }

     protected void callHome(){
         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
         startActivity(intent);
     }

         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }

         public void onClickSubmit(View view) {
            newSettings = new Bundle();

            if (plannerSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION){
                newSettings.putInt("planner_default", plannerSpinner.getSelectedItemPosition());
                Log.i("onClickSubmit", "Planner Spinner is at:" + plannerSpinner.getSelectedItemPosition());
            }
            if (weekSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION){
                newSettings.putInt("week_default", weekSpinner.getSelectedItemPosition());
                Log.i("onClickSubmit", "Week Spinner is at:" + weekSpinner.getSelectedItemPosition());
            }
            if (notificationSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION){
                newSettings.putInt("notification_default", notificationSpinner.getSelectedItemPosition());
                Log.i("onClickSubmit", "Notification Spinner is at:" + notificationSpinner.getSelectedItemPosition());
            }

            updateDB(newSettings);
         }

     protected void updateViews(Bundle update){
            plannerSpinner.setSelection(update.getInt("planner_default"));
            weekSpinner.setSelection(update.getInt("week_default"));
            notificationSpinner.setSelection(update.getInt("notification_default"));
     }

     class MyIncomingHandler extends Handler {
         @Override
         public void handleMessage(Message msg){
             int replyCode;
             Bundle payload = msg.getData();
             // Do some stuff with the variables and the reply codes

             replyCode = payload.getInt("reply");

             switch (replyCode){
                 case 202: // Settings found and included
                     existSettings = new Bundle();
                     existSettings.putInt("planner_default", payload.getInt("planner_default"));
                     existSettings.putInt("week_default", payload.getInt("week_default"));
                     existSettings.putInt("notification_default", payload.getInt("notification_default"));

                     updateViews(existSettings);
                     break;
                 case 203: // Settings not found
                     Toast.makeText(getApplicationContext(), "Settings database not found",Toast.LENGTH_SHORT).show();
                     break;
                 case 206: // Database updated
                     Toast.makeText(getApplicationContext(), "Settings updated", Toast.LENGTH_SHORT).show();
                     break;
                 case 207: // Database update failed
                     Toast.makeText(getApplicationContext(), "Settings failed to update", Toast.LENGTH_SHORT).show();
                     break;
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