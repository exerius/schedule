package com.example.shedule;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.work.Data;
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
    Uri link;
    Uri.Builder builder = new Uri.Builder();
    Gson gson = new Gson();
    Data dates;
    ArrayList<Couple> couples = new ArrayList<Couple>();
    Context context;
    public WorkerDownloader (Context context, WorkerParameters params){
        super(context, params);
        this.context = context;
    }
    @NonNull
    @Override
    public Result doWork(){

        builder.scheme("https")
                .authority("ruz.fa.ru")
                .appendPath("api")
                .appendPath("schedule")
                .appendPath("group")
                .appendPath("11995")
                .appendQueryParameter("start", getInputData().getString("monday"))
                .appendQueryParameter("finish", getInputData().getString("sunday"))
                .appendQueryParameter("lng", "1");
        link = builder.build();
        OkHttpClient client = new OkHttpClient();
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
        ScheduleDB db = ScheduleDB.getDataBase(this.context);
        CoupleDao dao = db.coupleDao();
        couples = (ArrayList<Couple>) gson.fromJson(dataString, new TypeToken<ArrayList<Couple>>(){}.getType());
        dao.deleteAll();
        for(Couple i: couples)
        {
            dao.insert(i);
        }
        return Result.success();
    }
}
