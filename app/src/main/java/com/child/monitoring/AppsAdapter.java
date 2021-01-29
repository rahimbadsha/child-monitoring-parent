package com.child.monitoring;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AppsAdapter extends ArrayAdapter<App> {

    public AppsAdapter(Context context, ArrayList<App> usageStatDTOArrayList) {
        super(context, 0, usageStatDTOArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        App usageStats = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.usage_stats_item, parent, false);
        }

        // Lookup view for data population
        TextView app_name_tv = convertView.findViewById(R.id.app_name_tv);
        TextView usage_duration_tv = convertView.findViewById(R.id.usage_duration_tv);
        TextView usage_perc_tv = convertView.findViewById(R.id.usage_perc_tv);
        ImageView icon_img = convertView.findViewById(R.id.icon_img);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        byte[] decodedString = Base64.decode(usageStats.logo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Drawable d = new BitmapDrawable(getContext().getResources(), decodedByte);
        // Populate the data into the template view using the data object
        app_name_tv.setText(usageStats.name);
        usage_duration_tv.setText(usageStats.hours);
        usage_perc_tv.setText(usageStats.percent + "%");
        icon_img.setImageDrawable(d);
        progressBar.setProgress(Integer.parseInt(usageStats.percent));

        // Return the completed view to render on screen
        return convertView;
    }
}
