package com.example.shedule;

import android.content.Context;
import android.util.Log;

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
    Gson gson = new Gson();
    ArrayList<Couple> couples = new ArrayList<Couple>();
    Context context;
    public WorkerDownloader (Context context, WorkerParameters params){
        super(context, params);
        this.context = context;
    }
    @Override
    public Result doWork(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ruz.fa.ru/api/schedule/group/11995?start=2022.02.07&finish=2022.02.12&lng=1")
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
