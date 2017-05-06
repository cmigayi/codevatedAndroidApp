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
 * Created by cilo on 4/21/17.
 */

public class CustomGridviewAllConcepts extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public CustomGridviewAllConcepts(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_gridview_all_concepts, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_gridview_all_concepts,null);
        TextView title = (TextView) convertView.findViewById(R.id.interest_title);
        TextView total = (TextView) convertView.findViewById(R.id.interest_total);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.interest_icon);

        HashMap<String,String> map = new HashMap<>();
        map = arrayList.get(position);

        title.setText(map.get("cartegory_name"));
        total.setText("Concepts: "+map.get("cartegory_total"));
        Picasso.with(context).load(map.get("cartegory_icon")).into(imageView);
        return convertView;
    }
}
