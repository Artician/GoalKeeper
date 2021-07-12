package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

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
}