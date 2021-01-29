package com.child.monitoring;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class AppUsageActivity extends Fragment {
    ListView apps_list;
    TextView usage_tv;
    BarChart barchart;
    SharedPreferences pref;
    String pid;
    String cid;
    protected View mView;
    ArrayList<App> apps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_app_usage, container, false);
        pref= getActivity().getSharedPreferences("childmonitor", Context.MODE_PRIVATE);
        pid = pref.getString("pid","");
        Intent intent = getActivity().getIntent();
        cid = intent.getStringExtra("cid");
        initUI(mView);
        new getAppData().execute(pid, cid);
        return mView;
    }

    private void initUI(View view) {
        barchart = (BarChart) view.findViewById(R.id.barchart);
        usage_tv = (TextView) view.findViewById(R.id.usage_tv);
        apps_list =(ListView) view.findViewById(R.id.apps_list);
    }

    public void showBarChart(ArrayList<App> appArrayList) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < appArrayList.size(); i++) {
            entries.add(new BarEntry(Integer.parseInt(appArrayList.get(i).percent), i));
        }

        BarDataSet bardataset = new BarDataSet(entries, "App Name");
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < appArrayList.size(); i++) {
            labels.add(appArrayList.get(i).name);
        }

        Legend legend = barchart.getLegend();
//        legend.setTextSize(2f);

        XAxis xAxis = barchart.getXAxis();
        xAxis.setTextSize(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxis = barchart.getAxisRight();
        yAxis.setEnabled(false);

        BarData data = new BarData(labels, bardataset);
        barchart.setData(data);
        barchart.getLegend().setEnabled(false);
        barchart.setDescription("");
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        barchart.animateY(5000);
    }



    public void showAppsUsage() {
        showBarChart(apps);
        // build the adapter
        AppsAdapter adapter = new AppsAdapter(getContext(), apps);
//
//        // attach the adapter to a ListView
        ListView listView = mView.findViewById(R.id.apps_list);
        listView.setAdapter(adapter);
    }

    private class getAppData extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            RestAPI restAPI = new RestAPI();
            try {
                JSONObject json = restAPI.getappusage(strings[0], strings[1]);
                JSONPARSE jp = new JSONPARSE();
                data = jp.parse(json);
                Log.d("RESPONSE", data.toString());
            } catch (Exception e) {
                Log.d("RESPONSE", e.getMessage());
                data = e.getMessage();
            }
            return data;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("RESPONSE:-", s);
            try {

                JSONObject json = new JSONObject(s);
                String StatusValue = json.getString("status");
                if (StatusValue.compareTo("ok") == 0) {
                    JSONArray jsonArray = json.getJSONArray("Data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        apps.add(new App(
                                jsonObj.getString("data0"),
                                jsonObj.getString("data1"),
                                jsonObj.getString("data2"),
                                jsonObj.getString("data3"),
                                jsonObj.getString("data4"),
                                jsonObj.getString("data5"),
                                jsonObj.getString("data6")
                        ));
                    }
                    showAppsUsage();

                } else {
                    Log.d("RESPONSE:-exe", "Something Went Wrong");
                }
            } catch (Exception e) {
                Log.d("RESPONSE:-exe", e.getMessage());
            }
        }
    }
}