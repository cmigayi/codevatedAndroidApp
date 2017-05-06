package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cilo on 4/24/17.
 */

public class CustomListviewAllDiscussions extends ArrayAdapter {
    String [] items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewAllDiscussions(Context context, String[] items) {
        super(context, R.layout.custom_listview_all_discussions, items);
        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_all_discussions,null);
        TextView topic = (TextView) convertView.findViewById(R.id.topic);
        topic.setText(items[position]);
        return convertView;
    }
}
