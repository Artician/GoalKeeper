package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class AppMonth extends AppCompatActivity {
    List<CardView> Labels;
    List<CardView> DayCards;
    List<TableRow> monthRows;
    TextView displayMonth, displayYear;


    // Overview:
    // This activity will display a monthly view of the calendar. Tapping on a week's row number
    // should call the Week view activity with user defaults, and tapping on a day should bring up
    // that day's Day activity. There should also be a shortcut for long-term goals.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_month);

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