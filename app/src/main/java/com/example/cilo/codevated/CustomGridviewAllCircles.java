package com.example.cilo.codevated;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/21/17.
 */

public class CustomGridviewAllCircles extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public CustomGridviewAllCircles(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_gridview_all_circles, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_gridview_all_circles,null);
        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        TextView membersText = (TextView) convertView.findViewById(R.id.members);
        TextView postsText = (TextView) convertView.findViewById(R.id.posts);
        HashMap<String,String> map = new HashMap<>();
        map = arrayList.get(position);

        titleText.setText(map.get("circle_name"));
        membersText.setText("members: "+map.get("members"));
        postsText.setText("posts: "+map.get("posts"));
        return convertView;
    }
}
