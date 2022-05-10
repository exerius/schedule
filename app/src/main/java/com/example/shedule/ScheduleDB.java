package com.example.shedule;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Couple.class}, version = 1)
public abstract class ScheduleDB extends RoomDatabase {
    static ScheduleDB INSTANCE;
    public abstract CoupleDao coupleDao();
    public static ScheduleDB getDataBase(final Context context){
        if (INSTANCE == null) {
            synchronized (ScheduleDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScheduleDB.class, "ScheduleDB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

