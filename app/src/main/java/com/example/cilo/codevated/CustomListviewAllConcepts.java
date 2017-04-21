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

public class CustomListviewAllConcepts extends ArrayAdapter {
    String [] items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewAllConcepts(Context context, String[] items) {
        super(context, R.layout.custom_listview_latest_post, items);

        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_latest_post,null);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView viewNew = (TextView) convertView.findViewById(R.id.view);
        viewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        title.append(items[position]);
        return convertView;
    }
}
