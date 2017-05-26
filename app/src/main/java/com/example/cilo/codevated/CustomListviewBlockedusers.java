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
 * Created by cilo on 5/11/17.
 */

public class CustomListviewBlockedusers extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView usernameTv;
    ImageView profImg;

    public CustomListviewBlockedusers(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_listview_blocked_users, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_blocked_users,null);
        profImg = (ImageView) convertView.findViewById(R.id.prof_img);
        usernameTv = (TextView) convertView.findViewById(R.id.usernameTv);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);
        usernameTv.setText(hashMap.get("username"));
        Picasso.with(context).load(hashMap.get("prof_img")).transform(new CircleTransform()).into(profImg);

        return convertView;
    }
}
