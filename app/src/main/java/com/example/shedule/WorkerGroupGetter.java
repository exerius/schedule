package com.example.shedule;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkerGroupGetter extends Worker {
    private final Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;
    String dataString;
    Uri link;
    Uri.Builder builder = new Uri.Builder();
    public WorkerGroupGetter(Context context, WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        prefs = context.getSharedPreferences("preferences", MODE_PRIVATE);
        ed = prefs.edit();
        builder.scheme("https")
                .authority("ruz.fa.ru")
                .appendPath("api")
                .appendPath("search")
                .appendQueryParameter("term", getInputData().getString("group_name"))
                .appendQueryParameter("type", "group")
                .build();
        link = builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(link.toString())
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            dataString = Objects.requireNonNull(response.body()).string();
            JSONArray jsonArray = new JSONArray(dataString);
            ed.putString("groupId", jsonArray.getJSONObject(0).getString("id"));
            ed.putString("groupName", getInputData().getString("group_name"));
            ed.commit();
        }
        catch (IOException | JSONException ignored)
        {

        }
        return Result.success();
    }
}
