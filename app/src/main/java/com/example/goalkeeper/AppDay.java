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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.MissingResourceException;

public class AppDay extends AppCompatActivity {
    MyIncomingHandler incomingHandler = new MyIncomingHandler();
    boolean isBoundPlanner = false;
    Messenger requestForPlanner;
    Messenger replyFromPlanner = new Messenger(incomingHandler);
    ServiceConnection plannerServiceConnection = new PlannerServiceConenction();

    TextView displayDate, displayDay, displayMonth, displayYear;
    Bundle eventsBundle;
    Calendar today;
    DateUtils duObject = new DateUtils();

    // Overview:
    // This activity's main purpose is to display a detailed view of all goals and events the user
    // has scheduled for that day. It also should have a shortcut button for the "daily" section of
    // the goals menu. Current version will show a recycler view with all current events, or an
    // empty list with the option to create a new event.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_day);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_planner, menu);
        menu.findItem(R.id.PA_day_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.PA_add_event_button:
                return true;
            case R.id.PA_week_button:
                callPlannerWeek();
                return true;
            case R.id.PA_month_button:
                callPlannerMonth();
                return true;
            case R.id.PA_home_button:
                callHome();
                return true;
            case R.id.PA_settings_button:
                callSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void init(){
        displayDate  = (TextView) findViewById(R.id.DA_dateText);
        displayDay   = (TextView) findViewById(R.id.DA_dayText);
        displayMonth = (TextView) findViewById(R.id.DA_monthText);
        displayYear  = (TextView) findViewById(R.id.DA_yearText);

        today = Calendar.getInstance();
        updateViewsToDate();

        try{
            isBoundPlanner = bindPlanner();
            if(!isBoundPlanner){
                throw new MissingResourceException("Planner service failed to bind", "MainActivity.class", "bad_bind");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }
    }

    protected boolean bindPlanner(){
        Intent intentPlanner = new Intent(getApplicationContext(), AppPlannerService.class);
        return bindService(intentPlanner, plannerServiceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void callHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    protected void callSettings(){
        Intent intent = new Intent(getApplicationContext(), AppSettings.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    protected void callPlannerMonth(){
        Intent intent = new Intent(getApplicationContext(), AppMonth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    protected void callPlannerWeek(){
        Intent intent = new Intent(getApplicationContext(), AppWeek.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    protected void callAddEvent(){

    }


    protected void updateViewsToDate(){
        String day      = duObject.getDay(today.get(Calendar.DAY_OF_WEEK));
        String month    = duObject.getMonth(today.get(Calendar.MONTH));

        displayDate.setText(Integer.toString(today.get(Calendar.DATE)));
        displayYear.setText(Integer.toString(today.get(Calendar.YEAR)));
        displayDay.setText(day);
        displayMonth.setText(month);
    }

    // Data manipulation
    public void onClickDayGoal(View view) {
    }

    class MyIncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            Bundle payload = msg.getData();
            // Do some stuff with the variables and the reply codes

            switch (payload.getInt("reply")){
                case AppUtils.READ_OK_RESULT_INCLUDED: // Settings found and included
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
        }
    }

    class PlannerServiceConenction implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    // To do:
    //  1.) onClick for floating add button calls NewEvent with no settings
    //  2.) onClick for an event calls NewEvent activity with settings for that event
    //  3.) onClick for goalButton calls DayGoalView
}