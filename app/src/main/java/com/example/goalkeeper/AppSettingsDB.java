package com.example.goalkeeper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AppSettingsEntity.class}, version = 1)
public abstract class AppSettingsDB extends RoomDatabase {
    public abstract AppSettingsDAO appSettingsDAO();
}
