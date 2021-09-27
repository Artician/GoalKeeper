package com.example.goalkeeper;

import java.util.Calendar;

public class DateUtils {
    // Any repeated functions that appear over and over through the code go here for cleanup
    public String getMonth(int calendarID) throws IllegalStateException{
        switch (calendarID){
            case Calendar.JANUARY:
                return "Jan";
            case Calendar.FEBRUARY:
                return "Feb";
            case Calendar.MARCH:
                return "Mar";
            case Calendar.APRIL:
                return "Apr";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "Aug";
            case Calendar.SEPTEMBER:
                return "Sept";
            case Calendar.OCTOBER:
                return "Oct";
            case Calendar.NOVEMBER:
                return "Nov";
            case Calendar.DECEMBER:
                return "Dec";
            default:
                throw new IllegalStateException("Unexpected value: " + calendarID);
        }
    }
    public int getMonthCode(String monthString) throws IllegalStateException{
        switch (monthString){
            case "Jan":
                return Calendar.JANUARY;
            case "Feb":
                return Calendar.FEBRUARY;
            case "Mar":
                return Calendar.MARCH;
            case "Apr":
                return Calendar.APRIL;
            case "May":
                return Calendar.MAY;
            case "June":
                return Calendar.JUNE;
            case "July":
                return Calendar.JULY;
            case "Aug":
                return Calendar.AUGUST;
            case "Sept":
                return Calendar.SEPTEMBER;
            case "Oct":
                return Calendar.OCTOBER;
            case "Nove":
                return Calendar.NOVEMBER;
            case "Dec":
                return Calendar.DECEMBER;
            default:
                throw new IllegalStateException("Unexpected value: " + monthString);
        }
    }
    public String getDay(int calendarID) throws IllegalStateException{
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
                throw new IllegalStateException("Unexpected value: " + calendarID);
        }
    }
}
