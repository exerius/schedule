package com.example.shedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {
    String string;
    ArrayList<Couple> couples = new ArrayList<Couple>();
    RecyclerView recyclerView;
    Couple couple;
    ScheduleDB db;
    CoupleDao dao;
    LiveData<List<Couple>> liveList;
    CoupleViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WorkRequest workerDownloader = new OneTimeWorkRequest.Builder(WorkerDownloader.class).build();
        setContentView(R.layout.activity_main);
        //couple = new Couple("math", "123", "man", "big", "Лекция", "11:00", "12:00","09.10", "123");
        //couples.add(couple);
        CoupleAdapter adapter = new CoupleAdapter(new CoupleAdapter.CoupleDiff(), couples);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        db = ScheduleDB.getDataBase(this);
        dao = db.coupleDao();
        model = new ViewModelProvider(this).get(CoupleViewModel.class);
        model.getLivedata().observe(this, couples1 -> {
            adapter.couples = couples1;
        });
        WorkManager.getInstance(this).enqueue(workerDownloader);

    }
}