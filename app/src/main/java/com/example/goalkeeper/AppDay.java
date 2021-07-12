package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class AppDay extends AppCompatActivity {
    TextView displayDate, displayDay, displayMonth, displayYear;
    Bundle eventsBundle;
    Calendar today;

    // Overview:
    // This activity's main purpose is to display a detailed view of all goals and events the user
    // has scheduled for that day. It also should have a shortcut button for the "daily" section of
    // the goals menu. Current version will show a recycler view with all current events, or an
    // empty list with the option to create a new event.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_day);
        displayDate  = (TextView) findViewById(R.id.DA_dateText);
        displayDay   = (TextView) findViewById(R.id.DA_dayText);
        displayMonth = (TextView) findViewById(R.id.DA_monthText);
        displayYear  = (TextView) findViewById(R.id.DA_yearText);

        today = Calendar.getInstance();
        updateViewsToDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.GA_home_button:
                callHome();
                return true;
            case R.id.GA_settings_button:
                callSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void callHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    protected void callSettings(){
        Intent intent = new Intent(getApplicationContext(), AppSettings.class);
        startActivity(intent);
    }


    protected void updateViewsToDate(){
        String day      = getDay(today.get(Calendar.DAY_OF_WEEK));
        String month    = getMonth(today.get(Calendar.MONTH));

        displayDate.setText(Integer.toString(today.get(Calendar.DATE)));
        displayYear.setText(Integer.toString(today.get(Calendar.YEAR)));
        displayDay.setText(day);
        displayMonth.setText(month);
    }

    protected String getDay(int calendarID) throws IllegalStateException{
        switch (calendarID){
            case Calendar.SUNDAY:
                return  "Sunday";
            case Calendar.MONDAY:
                return  "Monday";
            case Calendar.TUESDAY:
                return  "Tuesday";
            case Calendar.WEDNESDAY:
                return  "Wednesday";
            case Calendar.THURSDAY:
                return  "Thursday";
            case Calendar.FRIDAY:
                return  "Friday";
            case Calendar.SATURDAY:
                return  "Saturday";
            default:
                throw new IllegalStateException("Unexpected value: " + today.get(Calendar.DAY_OF_WEEK));
        }
    }
    protected String getMonth(int calendarID) throws IllegalStateException{
        switch (calendarID){
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                throw new IllegalStateException("Unexpected value: " + calendarID);
        }
    }

    // Data manipulation

    public void onClickDayGoal(View view) {
    }

    // To do:
    //  1.) onClick for floating add button calls NewEvent with no settings
    //  2.) onClick for an event calls NewEvent activity with settings for that event
    //  3.) onClick for goalButton calls DayGoalView
}