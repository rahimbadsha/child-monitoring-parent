package com.child.monitoring;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Nevon Dell on 4/3/2017.
 */

public class DataAdapter extends ArrayAdapter {

    String[] name;
    Context con;
    String n;

    TextView dname;
    ImageButton remove_name;

    public DataAdapter(Context context, String[] name) {
        super(context,R.layout.list_row_layout,name);

        this.name = name;
        con = context;
    }
    @SuppressLint("WrongViewCast")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(con);
        View v = inflater.inflate(R.layout.list_row_layout,null,true);

        dname = (TextView) v.findViewById(R.id.child_name);
        remove_name =(ImageButton) v.findViewById(R.id.remove_name);
        dname.setText(Html.fromHtml("<b>Name : </b>"+" "+(name[position])));
        return  v;
    }
}
