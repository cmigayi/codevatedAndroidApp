package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by cilo on 4/21/17.
 */

public class CustomGridviewAllCircles extends ArrayAdapter {
    String [] items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomGridviewAllCircles(Context context, String[] items) {
        super(context, R.layout.custom_gridview_all_circles, items);

        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_gridview_all_circles,null);
        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        titleText.setText(items[position]);
        return convertView;
    }
}
