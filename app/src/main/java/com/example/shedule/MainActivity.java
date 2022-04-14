package com.example.shedule;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String curMonday, curSunday;
    EditText group;
    ArrayList<Couple> couples = new ArrayList<>();
    RecyclerView recyclerView;
    ScheduleDB db;
    CoupleDao dao;
    CoupleViewModel model;
    TextView date;
    int Year, Day, Month;
    public void settings(View view) {
    }
    public void setWeek(View v) {
        final String[] sunday = new String[1];
        final String[] monday = new String[1];
        final Calendar cur = Calendar.getInstance();
        Year = cur.get(Calendar.YEAR);
        Month = cur.get(Calendar.MONTH);
        Day = cur.get(Calendar.DAY_OF_MONTH);
        cur.setFirstDayOfWeek(Calendar.MONDAY);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    cur.set(year, monthOfYear, dayOfMonth);
                    monday[0] = getMonday(cur.getTime());
                    sunday[0] = getSunday(cur.getTime());
                    curMonday = monday[0];
                    curSunday = sunday[0];
                    String editTextDateParam = monday[0]+"-"+sunday[0];
                    date.setText(editTextDateParam);
                    Download();
                }, Year, Month, Day);
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        datePickerDialog.show();
    }
    public boolean isConnected() {
        boolean connected;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return false;
    }
    public void nextWeek(View v){
        LocalDate nextMonday, nextSunday;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        nextMonday = LocalDate.parse(curMonday, dtf).plusDays(7);
        nextSunday = LocalDate.parse(curSunday, dtf).plusDays(7);
        curMonday = nextMonday.format(dtf);
        curSunday = nextSunday.format(dtf);
        Download();
    }

    private void Download() {
        WorkRequest workerDownloader = new OneTimeWorkRequest.Builder(WorkerDownloader.class)
                .setInputData(
                        new Data.Builder()
                                .putString("monday", curMonday)
                                .putString("sunday", curSunday)
                                .build()
                )

                .build();
        if (isConnected()) {
            WorkManager.getInstance(this).enqueue(workerDownloader);
        }
    }

    public void previousWeek(View v){
        LocalDate nextMonday, nextSunday;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        nextMonday = LocalDate.parse(curMonday, dtf).minusDays(7);
        nextSunday = LocalDate.parse(curSunday, dtf).minusDays(7);
        curMonday = nextMonday.format(dtf);
        curSunday = nextSunday.format(dtf);
        Download();
    }
    public void setGroup(View v){
            WorkRequest getGroup = new OneTimeWorkRequest.Builder(WorkerGroupGetter.class)
                    .setInputData(
                            new Data.Builder()
                                .putString("group_name", group.getText().toString())
                                .build()
                    )
                    .build();
        if (isConnected()) {
            WorkManager.getInstance(this).enqueue(getGroup);
        }
        Download();
    }
    @SuppressLint("SimpleDateFormat")
    public String getMonday(Date date){
        String monday;
        int days_from_monday;
        Calendar cur = Calendar.getInstance();

        cur.setTime(date);
        days_from_monday = cur.get(Calendar.DAY_OF_WEEK)-2;
        cur.add(Calendar.DAY_OF_WEEK, -days_from_monday);
        monday = new SimpleDateFormat("yyyy.MM.dd").format(cur.getTime());
        return monday;
    }
    @SuppressLint("SimpleDateFormat")
    public String getSunday(Date date){
        String sunday;
        int days_till_sunday;
        Calendar cur = Calendar.getInstance();
        cur.setTime(date);
        days_till_sunday = 8-cur.get(Calendar.DAY_OF_WEEK);
        cur.add(Calendar.DAY_OF_WEEK, days_till_sunday);
        sunday = new SimpleDateFormat("yyyy.MM.dd").format(cur.getTime());
        return sunday;
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.textView);
        group = findViewById(R.id.editTextTextPersonName);
        recyclerView = findViewById(R.id.list);
        CoupleAdapter adapter = new CoupleAdapter(new CoupleAdapter.CoupleDiff(), couples);
        recyclerView.setAdapter(adapter);
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        editor = preferences.edit();
        group.setText(preferences.getString("groupName", "ПИ20-5"));
        Objects.requireNonNull(getSupportActionBar()).hide();
        Calendar cal = Calendar.getInstance();
        curMonday = getMonday(cal.getTime());
        curSunday = getSunday(cal.getTime());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        db = ScheduleDB.getDataBase(this);
        dao = db.coupleDao();
        model = new ViewModelProvider(this).get(CoupleViewModel.class);
        Download();
        model.getLivedata().observe(this, couples1 -> {
            adapter.couples = couples1;
            adapter.notifyDataSetChanged();
        });
    }
}