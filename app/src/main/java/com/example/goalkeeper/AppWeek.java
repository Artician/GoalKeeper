package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AppWeek extends AppCompatActivity {
    // TextView variables
    TextView firstDay, firstMonth, secondDay, secondMonth;

    Bundle eventsBundle;
    Calendar today, firstDayWeek, lastDayWeek;

    DateUtils duObject = new DateUtils();

    //  Overview:
    //  This activity should serve as an overview of the coming days and their schedule. It will
    //  gain a variable from IO that determines the mode it loads in, either displaying:
    //  The current day, and the day before and after (3 day), the current M-F period (5 day) and
    //  the current full week (7 day). It should also have a button for weekly goals.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_week);

        firstDay    = (TextView) findViewById(R.id.WA_firstDay);
        firstMonth  = (TextView) findViewById(R.id.WA_firstMonth);
        secondDay   = (TextView) findViewById(R.id.WA_secondDay);
        secondMonth = (TextView) findViewById(R.id.WA_secondMonth);

        today = Calendar.getInstance();
        setWeek(today);
        updateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_planner, menu);
        menu.findItem(R.id.PA_week_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.PA_add_event_button:
                return true;
            case R.id.PA_day_button:
                callPlannerDay();
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

    protected void callPlannerDay(){
        Intent intent = new Intent(getApplicationContext(), AppDay.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    protected void callPlannerMonth(){
        Intent intent = new Intent(getApplicationContext(), AppMonth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    protected void callAddEvent(){

    }

    protected void setWeek(Calendar givenDay) throws IllegalStateException{
        switch (givenDay.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                lastDayWeek.add(Calendar.DATE, 6);
                break;
            case Calendar.MONDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                firstDayWeek.add(Calendar.DATE, -1);
                lastDayWeek.add(Calendar.DATE, 5);
                break;
            case Calendar.TUESDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                firstDayWeek.add(Calendar.DATE, -2);
                lastDayWeek.add(Calendar.DATE, 4);
                break;
            case Calendar.WEDNESDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                firstDayWeek.add(Calendar.DATE, -3);
                lastDayWeek.add(Calendar.DATE, 3);
                break;
            case Calendar.THURSDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                firstDayWeek.add(Calendar.DATE, -4);
                lastDayWeek.add(Calendar.DATE, 2);
                break;
            case Calendar.FRIDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                firstDayWeek.add(Calendar.DATE, -5);
                lastDayWeek.add(Calendar.DATE, 1);
                break;
            case Calendar.SATURDAY:
                firstDayWeek = (Calendar) givenDay.clone();
                lastDayWeek = (Calendar) givenDay.clone();
                firstDayWeek.add(Calendar.DATE, -6);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + today.get(Calendar.DAY_OF_WEEK));
        }
    }

    protected void updateViews(){
        String firstMonthText = duObject.getMonth(firstDayWeek.get(Calendar.MONTH));
        String secondMonthText = duObject.getMonth(lastDayWeek.get(Calendar.MONTH));

        firstDay.setText(Integer.toString(firstDayWeek.get(Calendar.DATE)));
        firstMonth.setText(firstMonthText);
        secondDay.setText(Integer.toString(lastDayWeek.get(Calendar.DATE)));
        secondMonth.setText(secondMonthText);
    }

    class MyIncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            int replyCode;
            Bundle payload = msg.getData();
            // Do some stuff with the variables and the reply codes

            replyCode = payload.getInt("reply");

            switch (replyCode){
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
    //  1.) onClick for goalButton brings up Weekly goals
    //  2.) onClick for a day brings up Day view
    //  3.) onClick for 3, 5, and 7 day variants should change between each view by destroying/recreating recyclerview items
    //  4.) onClick for each arrow button should
}