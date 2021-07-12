package com.example.goalkeeper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AppEventsEntity.class}, version = 1)
public abstract class AppEventsDB extends RoomDatabase {
    public abstract AppEventsDAO appEventsDAO();
}
