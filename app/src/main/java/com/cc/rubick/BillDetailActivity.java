package com.cc.rubick;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.rubick.model.BillAdapter;
import com.cc.rubick.util.MyRestTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BillDetailActivity extends AppCompatActivity {
    MyRestTemplate myRestTemplate;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        myRestTemplate = new MyRestTemplate(this);
        Intent i = getIntent();
        String label = i.getStringExtra("label");
        Float time = i.getFloatExtra("time", 0f);
        new BillDetailTask().execute(label, time.intValue() + "");


        recyclerView = (RecyclerView) findViewById(R.id.billDetailList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private class BillDetailTask extends AsyncTask<String, String, String> {
        JsonArray array;

        @Override
        protected String doInBackground(String... params) {
            String key = params[0];
            String time = params[1];
            String dateBegin = "";
            String dateEnd = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();

            if (time.length() == 4) {
                calendar.set(Integer.valueOf(time), 0, 0);
                dateBegin = dateFormat.format(calendar.getTime());
                calendar.add(Calendar.YEAR, 1);
                dateEnd = dateFormat.format(calendar.getTime());


            } else if (time.length() == 6) {
                calendar.set(Integer.valueOf(time.substring(0, 4)), Integer.valueOf(time.substring(time.length() - 2, time.length())), 0);
                dateBegin = dateFormat.format(calendar.getTime());
                calendar.add(Calendar.MONTH, 1);
                dateEnd = dateFormat.format(calendar.getTime());

            } else if (time.length() == 8) {
                calendar.set(Integer.valueOf(time.substring(0, 4)), Integer.valueOf(time.substring(time.length() - 4, time.length() - 2)), Integer.valueOf(time.substring(time.length() - 2, time.length())));
                dateBegin = dateFormat.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                dateEnd = dateFormat.format(calendar.getTime());

            }


            String url = "pugna/transactionRecord/list?transationCreateTimeEnd=" + dateEnd + "&transationCreateTimeBegin=" + dateBegin + "&paymentType=" + key + "&page.showCount=1000";

            if (key == null) {
                url = "pugna/callRecords/list?transationCreateTimeEnd=" + dateEnd + "&transationCreateTimeBegin=" + dateBegin + "&page.showCount=1000";
            }
            Log.v("url", url);

            JsonObject jsonObject = myRestTemplate.get(url);

            try {
                Log.v("url", jsonObject.toString());

                array = jsonObject.get("list").getAsJsonArray();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            BillAdapter billAdapter = new BillAdapter(array);


            recyclerView.setAdapter(billAdapter);


        }
    }
}
