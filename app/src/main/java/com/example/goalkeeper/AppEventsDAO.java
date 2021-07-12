package com.example.goalkeeper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppEventsDAO {
    @Query("SELECT * FROM AppEventsEntity ORDER BY eventID")
    List<AppEventsEntity> loadAllEvents();

    @Insert
    void insertEvent(AppEventsEntity event);

    @Update
    void updateEvent(AppEventsEntity event);

    @Delete
    int deleteEvent(AppEventsEntity event);
}
