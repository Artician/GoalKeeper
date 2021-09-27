package com.example.goalkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.MissingResourceException;

public class AppAddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    MyIncomingHandler incomingHandler = new MyIncomingHandler();
    boolean isBoundIO = false;
    Messenger requestForIO;
    Messenger replyFromIO = new Messenger(incomingHandler);
    ServiceConnection ioServiceConnection = new IOServiceConnection();

    SharedPreferences preferences;
    Bundle eventForIO;
    Calendar startTime, endTime;
    // View handlers

    TextView startMonth, startYear, startDate, startHour, startMinute, endMonth, endYear, endDate, endHour, endMinute;
    EditText editTitle, editLocation;
    CheckBox copyDateCheck, repeatCheck, remindCheck;
    Spinner durationSpinner, repeatIntervalSpinner, remindIntervalSpinner;
    Button endDateButton, endTimeButton;

    String[] durationOptions = {"00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45", "02:00", "02:15", "02:30", "02:45", "03:00",
            "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45", "06:00", "06:15", "06:30", "06:45",
            "07:00", "07:15", "07:30", "07:45", "08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30",
            "10:45", "11:00", "11:15", "11:30", "11:45", "12:00"};
    String[] repeatOptions = {"Every day", "Every week", "Work week (M-F)", "Weekends"};
    String[] remindOptions = {"00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45", "02:00", "02:15", "02:30", "02:45", "03:00",
            "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45", "06:00"};

    ArrayAdapter<String> durationAdapter, repeatAdapter, remindAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_add_event);

        init();
    }
    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onStart(){
        super.onStart();
        init();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_planner, menu);
        menu.findItem(R.id.PA_month_button).setVisible(false);
        menu.findItem(R.id.PA_week_button).setVisible(false);
        menu.findItem(R.id.PA_day_button).setVisible(false);
        menu.findItem(R.id.PA_add_event_button).setVisible(false);
        menu.findItem(R.id.PA_settings_button).setVisible(false);
        menu.findItem(R.id.PA_cancel_button).setVisible(true);
        menu.findItem(R.id.PA_cancel_button).setEnabled(true);
        menu.findItem(R.id.PA_cancel_button).setCheckable(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.PA_cancel_button:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void init(){
        startDate               = (TextView) findViewById(R.id.AE_start_date_text);
        startMonth              = (TextView) findViewById(R.id.AE_start_month_text);
        startYear               = (TextView) findViewById(R.id.AE_start_year_text);
        startHour               = (TextView) findViewById(R.id.AE_start_hour_text);
        startMinute             = (TextView) findViewById(R.id.AE_start_min_text);
        endDate                 = (TextView) findViewById(R.id.AE_end_date_text);
        endMonth                = (TextView) findViewById(R.id.AE_end_month_text);
        endYear                 = (TextView) findViewById(R.id.AE_end_year_text);
        endHour                 = (TextView) findViewById(R.id.AE_end_hour_text);
        endMinute               = (TextView) findViewById(R.id.AE_end_min_text);

        editTitle               = (EditText) findViewById(R.id.AE_edit_title);
        editLocation            = (EditText) findViewById(R.id.AE_edit_location);

        copyDateCheck           = (CheckBox) findViewById(R.id.AE_copy_date_check);
        repeatCheck             = (CheckBox) findViewById(R.id.AE_repeat_check);
        remindCheck             = (CheckBox) findViewById(R.id.AE_remind_check);

        endDateButton           = (Button) findViewById(R.id.AE_end_date_button);
        endTimeButton           = (Button) findViewById(R.id.AE_end_time_button);

        durationSpinner         = (Spinner) findViewById(R.id.AE_duration_spinner);
        repeatIntervalSpinner   = (Spinner) findViewById(R.id.AE_repeat_interval_spinner);
        remindIntervalSpinner   = (Spinner) findViewById(R.id.AE_remind_interval_spinner);

        copyDateCheck.setChecked(true);
        toggleEndButton(copyDateCheck.isChecked());

        durationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durationOptions);
        repeatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, repeatOptions);
        remindAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, remindOptions);

        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        durationSpinner.setOnItemSelectedListener(this);
        repeatIntervalSpinner.setOnItemSelectedListener(this);
        remindIntervalSpinner.setOnItemSelectedListener(this);

        durationSpinner.setAdapter(durationAdapter);
        repeatIntervalSpinner.setAdapter(repeatAdapter);
        remindIntervalSpinner.setAdapter(remindAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent callIntent = getIntent();
        Calendar fromIntent = Calendar.getInstance();

        switch (callIntent.getStringExtra("source_date")){
            case "today":
                populateDates(fromIntent);
                break;
            case "other":

            default:
                Log.e("AppAddEvent/init()", "Invalid source");
                break;
        }



        try{
            isBoundIO = bindIO();

            if(!isBoundIO){
                throw new MissingResourceException("IO Service failed to bind", "AppSettings.class", "bad_bind");
            }
        } catch (MissingResourceException e){
            e.printStackTrace();
        }

    }

    protected boolean bindIO(){
        Intent intentIO = new Intent(getApplicationContext(), AppIO.class);
        return bindService(intentIO, ioServiceConnection, Context.BIND_AUTO_CREATE);

    }

    void unBindIO(){
        if(isBoundIO){
            unbindService(ioServiceConnection);
            isBoundIO = false;
        }
    }

    protected void populateDates(Calendar date){
        startDate.setText(Integer.toString(date.get(Calendar.DATE)));
        startMonth.setText(getMonth(date.get(Calendar.MONTH)));
        startYear.setText(Integer.toString(date.get(Calendar.YEAR)));
    }

    protected void copyDate(){
        endDate.setText(startDate.getText());
        endMonth.setText(startMonth.getText());
        endYear.setText(startYear.getText());

        endTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));
        endTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR));
    }

    protected void packageEvent(){
        eventForIO = new Bundle();

        eventForIO.putString("event_title", editTitle.getText().toString());
        eventForIO.putLong("start_time", startTime.getTimeInMillis());
        eventForIO.putLong("end_time", endTime.getTimeInMillis());
        eventForIO.putString("repeat_rule", "CHANGE THIS ASAP, JAKE!");
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

    protected void toggleEndButton(boolean check){
        if(check){
            endDateButton.setClickable(false);
            endDateButton.setAlpha(.5f);
        } else{
            endDateButton.setClickable(true);
            endDateButton.setAlpha(1f);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCLickCreateEvent(View view) {
    }

    public void onClickCopyDate(View view) {
        toggleEndButton(copyDateCheck.isChecked());
    }

    public void onClickStartDate(View view) {
        DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickEndDate(View view) {
        DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickEndTime(View view) {
        TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
        newFragment.setFlag(TimePickerDialogFragment.FLAG_END_TIME);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickStartTime(View view) {
        TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
        newFragment.setFlag(TimePickerDialogFragment.FLAG_START_TIME);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickRemindCheck(View view) {
    }

    public void onClickRepeatCheck(View view) {
    }

    public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        public static final int FLAG_START_TIME = 0;
        public static final int FLAG_END_TIME = 1;

        private int flag;

        @NonNull
        @Override
        public Dialog onCreateDialog( Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            switch (flag){
                case FLAG_START_TIME:
                    startHour.setText(Integer.toString(hourOfDay));
                    startMinute.setText(Integer.toString(minute));

                    startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    startTime.set(Calendar.MINUTE, minute);
                    break;

                case FLAG_END_TIME:
                    endHour.setText(Integer.toString(hourOfDay));
                    endMinute.setText(Integer.toString(minute));

                    endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    endTime.set(Calendar.MINUTE, minute);
                    break;
                default:
                    break;
            }

        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }

    public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            switch (flag){
                case FLAG_START_DATE:
                    startDate.setText(Integer.toString(dayOfMonth));
                    startMonth.setText(getMonth(month));
                    startYear.setText(Integer.toString(year));

                    startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    startTime.set(Calendar.YEAR, year);
                    startTime.set(Calendar.MONTH, month);

                    if(copyDateCheck.isChecked()){
                        copyDate();
                    }
                    break;

                case FLAG_END_DATE:
                    endDate.setText(Integer.toString(dayOfMonth));
                    endMonth.setText(getMonth(month));
                    endYear.setText(Integer.toString(year));

                    endTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    endTime.set(Calendar.YEAR, year);
                    endTime.set(Calendar.MONTH, month);
                    break;

                default:
                    break;
            }

        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
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

    class IOServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            requestForIO = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            requestForIO = null;
        }
    }
}