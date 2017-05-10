package com.cc.rubick;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.rubick.util.MyRestTemplate;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    PieChart pieChart;

    String dateBegin;
    String dateEnd;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MyRestTemplate myRestTemplate;
    private Map<String, String> map;


    protected void onCreate(Bundle savedInstanceState) {
        myRestTemplate = new MyRestTemplate(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        pieChart = (PieChart) findViewById(R.id.chartId);
        Description description = new Description();
        description.setText("联系人图表");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        pieChart.setDescription(description);
        pieChart.setOnChartValueSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -30);
        Long day = (calendar.getTimeInMillis() - c.getTimeInMillis()) / 86400000;
        pieChart.setCenterText("近" + day + "天联系情况");
        new ChartTask().execute(c, calendar);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        final PieEntry pieEntry = (PieEntry) e;

        String key = "";
        for (String k : map.keySet()) {
            if (map.get(k).equals(((PieEntry) e).getLabel())) {
                key = k;
                break;
            }
        }

        Intent i = new Intent(this, CallRecordsActivity.class);
        i.putExtra("key", key);
        i.putExtra("dateBegin", dateBegin);
        i.putExtra("dateEnd", dateEnd);
        startActivity(i);


    }

    @Override
    public void onNothingSelected() {
        Intent i = new Intent(this, CallRecordsActivity.class);

        i.putExtra("dateBegin", dateBegin);
        i.putExtra("dateEnd", dateEnd);
        startActivity(i);


    }


    private class ChartTask extends AsyncTask<Calendar, Calendar, Calendar> {
        PieData data = null;

        @Override
        protected Calendar doInBackground(Calendar... params) {


            dateBegin = dateFormat.format(params[0].getTime());
            dateEnd = dateFormat.format(params[1].getTime());
            List<String> dayStrings = new ArrayList<>();
            Calendar c = params[0];
            while (true) {

                String formatStr = dateFormat.format(c.getTime());
                if (formatStr.equals(dateEnd)) {
                    break;
                }
                if (dayStrings.size() > 32) {
                    break;
                }
                c.add(Calendar.DAY_OF_YEAR, 1);
                dayStrings.add(formatStr);


            }


            JsonObject config = myRestTemplate.get("pugna/config/map?type=CALL_TYPE");
            map = new Gson().fromJson(config, Map.class);


            JsonObject jsonObject = myRestTemplate.get("pugna/callRecords/chart?dateEnd=" + dateEnd + "&dateBegin=" + dateBegin);
            PieDataSet dataSets = null;


            Map<String, Map<String, Double>> j = new Gson().fromJson(jsonObject, Map.class);
            Log.v("嘿嘿11", j.toString());
            List<PieEntry> arry = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();

            for (String key : j.keySet()) {
                String label;
                int t = j.get(key).size();
                Log.v("key", key);
                if (map.containsKey(key)) {
                    label = map.get(key);
                } else {
                    label = "其他";
                }


                if (key.equals("1")) {
                    //深红色 R=254 G=67 B=101
                    colors.add(Color.rgb(252, 157, 154));

                } else if (key.equals("2")) {
                    colors.add(Color.rgb(167, 220, 224));

                } else if (key.equals("3")) {
                    //R=249 G=205 B=173 淡黄色
                    colors.add(Color.rgb(249, 205, 173));

                } else {
                    //R=200 G=200 B=169 浅青色
                    colors.add(Color.rgb(200, 200, 169));

                }
                PieEntry pieEntry = new PieEntry(t, label);
                arry.add(pieEntry);


            }

            dataSets = new PieDataSet(arry, "");
            dataSets.setColors(colors);


            data = new PieData(dataSets);


            return null;

        }

        @Override
        protected void onPostExecute(Calendar c) {
            super.onPostExecute(c);
            pieChart.setData(data);
            pieChart.invalidate();

        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
