package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/16/17.
 */

public class CustomListviewMyDiscussionsContributions extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewMyDiscussionsContributions(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_listview_my_discussions_contributions, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_my_discussions_contributions,null);
        TextView topicTv = (TextView) convertView.findViewById(R.id.topic);
        TextView postedDate = (TextView) convertView.findViewById(R.id.posted_date);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);

        topicTv.setText(hashMap.get("topic"));
        postedDate.setText(hashMap.get("posted_date"));

        return convertView;
    }
}
