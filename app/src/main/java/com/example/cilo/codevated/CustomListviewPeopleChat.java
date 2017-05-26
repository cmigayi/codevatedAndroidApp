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
 * Created by cilo on 4/24/17.
 */

public class CustomListviewPeopleChat extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewPeopleChat(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_listview_people_chat, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_people_chat,null);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.user_img);
        ImageView imgRelation = (ImageView) convertView.findViewById(R.id.img_relation);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);

        username.setText(hashMap.get("username"));
        if(hashMap.get("prof_img").equals("") || hashMap.get("prof_img").equals("n")){

        }else{
            Picasso.with(context).load(hashMap.get("prof_img")).transform(new CircleTransform()).into(imageView);
        }

        if(hashMap.get("relation").equals("mentor")){
            imgRelation.setImageResource(R.drawable.mentor);
        }

        return convertView;
    }
}
