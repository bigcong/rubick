package com.cc.rubick;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.rubick.model.MyAdapter;
import com.cc.rubick.model.RecordEntity;
import com.cc.rubick.util.MyDate;
import com.cc.rubick.util.MyRestTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class CallRecordsActivity extends AppCompatActivity {
    MyDate myDate;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRestTemplate restTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_records);
        restTemplate = new MyRestTemplate(this);

        Intent i = getIntent();
        String key = i.getStringExtra("key");
        String dateBegin = i.getStringExtra("dateBegin");
        String dateEnd = i.getStringExtra("dateEnd");

        Log.v("传入的数据为", key + "," + dateBegin + "," + dateEnd);

        new CallRecordsTask().execute(key, dateBegin, dateEnd);

        mRecyclerView = (RecyclerView) findViewById(R.id.cc);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }


    private class CallRecordsTask extends AsyncTask<String, String, String> {
        JsonArray array;

        @Override
        protected String doInBackground(String... params) {
            String key = params[0];
            String dateBegin = params[1];
            String dateEnd = params[2];

            String url = "pugna/callRecords/list?dateEnd=" + dateEnd + "&dateBegin=" + dateBegin + "&type=" + key + "&page.showCount=1000";

            if (key == null) {
                url = "pugna/callRecords/list?dateEnd=" + dateEnd + "&dateBegin=" + dateBegin + "&page.showCount=1000";
            }
            Log.v("url", url);

            JsonObject jsonObject = restTemplate.get(url);

            try {
                Log.v("url",  jsonObject.toString());

                array = jsonObject.get("list").getAsJsonArray();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            Gson g = new Gson();

            List<RecordEntity> r = g.fromJson(array, new TypeToken<List<RecordEntity>>() {
            }.getType());


            mAdapter = new MyAdapter(r);
            mRecyclerView.setAdapter(mAdapter);

        }
    }


}
