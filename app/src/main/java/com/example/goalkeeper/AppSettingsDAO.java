package com.example.goalkeeper;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppSettingsDAO {
    @Query("SELECT * FROM AppSettingsEntity")
    List<AppSettingsEntity> getAllSettings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEntry(AppSettingsEntity entry);

    @Update
    void updateSetting(AppSettingsEntity setting);

    @Update
    int updateSettings(List<AppSettingsEntity> newSettings);
}
