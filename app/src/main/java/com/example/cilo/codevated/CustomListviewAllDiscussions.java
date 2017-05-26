package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/24/17.
 */

public class CustomListviewAllDiscussions extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewAllDiscussions(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_listview_all_discussions, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_all_discussions,null);
        TextView topic = (TextView) convertView.findViewById(R.id.topic);
        TextView postedDate = (TextView) convertView.findViewById(R.id.posted_date);

        HashMap<String,String> map = new HashMap<>();
        map = arrayList.get(position);

        topic.setText(map.get("topic"));
        postedDate.setText(", "+map.get("posted_date"));

        return convertView;
    }
}
