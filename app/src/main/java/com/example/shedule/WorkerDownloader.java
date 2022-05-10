package com.example.shedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkerDownloader extends Worker {
    String dataString;
    SharedPreferences prefs;
    Uri link;
    Uri.Builder builder = new Uri.Builder();
    Gson gson = new Gson();
    ArrayList<Couple> couples = new ArrayList<>();
    Context context;
    ScheduleDB db;
    CoupleDao dao;
    OkHttpClient client = new OkHttpClient();
    public WorkerDownloader (Context context, WorkerParameters params){
        super(context, params);
        this.context = context;
    }
    @NonNull
    @Override
    public Result doWork(){
        prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        builder.scheme("https")
                .authority("ruz.fa.ru")
                .appendPath("api")
                .appendPath("schedule")
                .appendPath("group")
                .appendPath(prefs.getString("groupId", "11995"))
                .appendQueryParameter("start", getInputData().getString("monday"))
                .appendQueryParameter("finish", getInputData().getString("sunday"))
                .appendQueryParameter("lng", "1");
        link = builder.build();
        Request request = new Request.Builder()
                .url(link.toString())
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            dataString = Objects.requireNonNull(response.body()).string();
        }
        catch (IOException ignored)
        {

        }
        db = ScheduleDB.getDataBase(this.context);
        dao = db.coupleDao();
        couples = (ArrayList<Couple>) gson.fromJson(dataString, new TypeToken<ArrayList<Couple>>(){}.getType());
        dao.deleteAll();
        for(Couple i: couples)
        {
            dao.insert(i);
        }
        return Result.success();
    }
}
