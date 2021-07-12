package com.example.goalkeeper;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AppSettingsEntity {
    @PrimaryKey @NonNull
    public String setting_name;

    @ColumnInfo(name = "value")
    public int value;
}
