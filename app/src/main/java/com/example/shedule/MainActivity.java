package com.example.shedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {
    ArrayList<Couple> couples = new ArrayList<Couple>();
    Gson gson = new Gson();
    class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://ruz.fa.ru/api/schedule/group/11995?start=2022.02.07&finish=2022.02.12&lng=1")
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                return Objects.requireNonNull(response.body()).string();
            }
            catch (IOException ignored)
            {

            }
            return "";
        }
        @Override
        protected void onPostExecute(String param){
            super.onPostExecute(param);
            couples = (ArrayList<Couple>) gson.fromJson(param, new TypeToken<ArrayList<Couple>>(){}.getType());
            CoupleAdapter adapter = new CoupleAdapter(MainActivity.this, couples);
            RecyclerView recyclerView = findViewById(R.id.list);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTask Task = new MyTask();
        Task.execute();
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.list);

    }
}