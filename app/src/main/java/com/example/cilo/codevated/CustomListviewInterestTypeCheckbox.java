package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cilo on 4/21/17.
 */

public class CustomListviewInterestTypeCheckbox extends ArrayAdapter {
    String [] items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewInterestTypeCheckbox(Context context, String[] items) {
        super(context, R.layout.custom_listview_interest_type_checkbox, items);

        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_interest_type_checkbox,null);
        TextView title = (TextView) convertView.findViewById(R.id.interest_title);
        ImageView iconImg = (ImageView) convertView.findViewById(R.id.interest_icon);

        title.append(items[position]);
        return convertView;
    }
}
