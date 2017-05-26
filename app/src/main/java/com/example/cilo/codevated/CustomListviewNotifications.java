package com.example.cilo.codevated;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by cilo on 4/23/17.
 */

public class CustomListviewNotifications extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView msgTitle,unreadTv,newTv,msgdate,senderName;
    ImageView imageView;
    Date date;

    public CustomListviewNotifications(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_listview_notifications, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_notifications,null);
        msgTitle = (TextView) convertView.findViewById(R.id.sender_msg_title);
        unreadTv = (TextView) convertView.findViewById(R.id.unread_tv);
        newTv = (TextView) convertView.findViewById(R.id.new_tv);
        msgdate = (TextView) convertView.findViewById(R.id.msgdate);
        senderName = (TextView) convertView.findViewById(R.id.sender_name);
        imageView = (ImageView) convertView.findViewById(R.id.sender_pic);

        date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String currentDate = dateFormatter.format(date);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);
        msgTitle.setText(hashMap.get("title"));
        msgdate.setText(hashMap.get("posted_date"));
        senderName.setText(hashMap.get("source"));
        if(hashMap.get("source").equals("Codevated team")){
            imageView.setImageResource(R.drawable.logo);
        }

        if(hashMap.get("status").equals("read")){
            unreadTv.setText("Read");
            unreadTv.setBackgroundColor(Color.parseColor("#dddddd"));
            unreadTv.setTextColor(Color.parseColor("#000000"));
        }

        if(hashMap.get("posted_date_two").equals(currentDate)){
            newTv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
