package com.cc.rubick;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cc.rubick.util.MyDate;
import com.cc.rubick.util.MyRestTemplate;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class BillActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private BarChart barChart;
    private MyDate mydata;
    MyRestTemplate restTemplate;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        barChart = (BarChart) findViewById(R.id.bill_bar_chart);
        mydata = (MyDate) getApplication();
        barChart.setOnChartValueSelectedListener(this);

        restTemplate = new MyRestTemplate(this);
        new BilltTask().execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setData(Map<String, Map<String, Double>> mapMap) {

        List<Integer> colors = Arrays.asList(getResources().getColor(R.color.淡青色), getResources().getColor(R.color.深红色), getResources().getColor(R.color.深红色), getResources().getColor(R.color.浅黄色));
        Map<String, Integer> colorsMap = new HashMap<>();
        int i = 0;
        for (String key : mapMap.keySet()) {
            colorsMap.put(key, colors.get(i));
            i++;

        }


        Log.v("setData", new Gson().toJson(mapMap));
        Set<String> set = new HashSet<>();
        mapMap.values().forEach(t -> t.keySet().forEach(g -> set.add(g)));
        List<String> xVals = set.stream().sorted().collect(Collectors.toList());
        ArrayList<IBarDataSet> threebardata = new ArrayList<>();
        for (String key : mapMap.keySet()) {
            Map<String, Double> map = mapMap.get(key);
            ArrayList<BarEntry> yVals = new ArrayList<>();
            xVals.forEach(t -> yVals.add(new BarEntry(Float.valueOf(t), map.getOrDefault(t, 0d).floatValue())));
            BarDataSet barDataSet = new BarDataSet(yVals, key);
            barDataSet.setColor(colorsMap.get(key));
            threebardata.add(barDataSet);

        }

        BarData bardata = new BarData(threebardata);
        bardata.setBarWidth(0.45f);
        barChart.setData(bardata);
        barChart.groupBars(2008f, 0.1f, 0.02f);
        barChart.animateXY(1000, 2000);//设置动画
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        barChart.invalidate();


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Bill Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.v("entry", e.getX() + "," + e.getY());
        String label = barChart.getData().getDataSetForEntry(e).getLabel();
        Intent intent = new Intent(this, BillDetailActivity.class);
        intent.putExtra("label", label);
        intent.putExtra("time", e.getX());
        startActivity(intent);

        Log.v("label", label);
    }

    @Override
    public void onNothingSelected() {
        Log.v("entry", "cccc");

    }

    private class BilltTask extends AsyncTask<String, String, String> {
        Map<String, Map<String, Double>> m;


        @Override
        protected String doInBackground(String... params) {
            String dateEnd = "";
            String dateBegin = "";


            JsonObject jsonObject = restTemplate.get("/transactionRecord/chart?dateEnd=" + dateEnd + "&dateBegin=" + dateBegin + "&page.showCount=10000");
            Gson g = new Gson();
            m = g.fromJson(jsonObject, Map.class);


            return null;
        }

        @Override
        protected void onPostExecute(String c) {
            setData(m);

            super.onPostExecute(c);

        }

    }


}
