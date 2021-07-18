package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AppMonth extends AppCompatActivity {
    List<CardView> Labels;
    List<CardView> DayCards;
    List<TableRow> monthRows;
    TextView displayMonth, displayYear;

    Bundle eventsBundle;
    Calendar today;

    // Overview:
    // This activity will display a monthly view of the calendar. Tapping on a week's row number
    // should call the Week view activity with user defaults, and tapping on a day should bring up
    // that day's Day activity. There should also be a shortcut for long-term goals.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_month);
        today = Calendar.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_planner, menu);
        menu.findItem(R.id.PA_month_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.PA_add_event_button:
            case R.id.PA_day_button:
            case R.id.PA_week_button:
            case R.id.PA_home_button:
            case R.id.PA_settings_button:

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

    // Data manipulation

    protected void populateLabelCards(){
        int cardWidth, cardHeight;
    }

    // onClick listeners
    public void onClickDateButton(View view) {
    }

    public void onClickMonthPrev(View view) {
    }

    public void onClickMonthNext(View view) {
    }

    class MyIncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            int replyCode;
            Bundle payload = msg.getData();
            // Do some stuff with the variables and the reply codes

            replyCode = payload.getInt("reply");

            switch (replyCode){
                case R.integer.READ_OK_RESULT_INCLUDED: // Settings found and included
                    break;
                case R.integer.READ_BAD_NO_DATA: // Settings not found
                    Toast.makeText(getApplicationContext(), "Settings database not found",Toast.LENGTH_SHORT).show();
                    break;
                case R.integer.DB_WRITE_OK: // Database updated
                    Toast.makeText(getApplicationContext(), "Settings updated", Toast.LENGTH_SHORT).show();
                    break;
                case R.integer.DB_WRITE_FAILED: // Database update failed
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
}