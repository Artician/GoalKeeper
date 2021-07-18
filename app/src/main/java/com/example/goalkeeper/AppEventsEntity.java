package com.example.goalkeeper;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class AppEventsEntity {

    @PrimaryKey(autoGenerate = true)
    public int eventID;

    @ColumnInfo
    public String calendar;

    @ColumnInfo
    public String eventTitle;

    @ColumnInfo
    public int startTime;

    @ColumnInfo
    public int endTime;

    @ColumnInfo
    public String RRule;

    @ColumnInfo
    public boolean hasParent;

    @ColumnInfo
    public int parentID;

    @ColumnInfo
    public boolean hasChild;

    @ColumnInfo
    public int childID;

    @ColumnInfo
    public String status;

    @ColumnInfo
    public String location;

    @ColumnInfo
    public String summary;

    @ColumnInfo
    public boolean doRemind;

    @ColumnInfo
    public int remindInterval;

}
