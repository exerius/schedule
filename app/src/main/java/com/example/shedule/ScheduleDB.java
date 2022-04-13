package com.example.shedule;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

