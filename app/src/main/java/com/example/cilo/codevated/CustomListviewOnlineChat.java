package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by cilo on 4/24/17.
 */

public class CustomListviewOnlineChat extends ArrayAdapter {
    String [] items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewOnlineChat(Context context, String[] items) {
        super(context, R.layout.custom_listview_online_chat, items);

        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_online_chat,null);
        TextView title = (TextView) convertView.findViewById(R.id.username);
        title.append(items[position]);
        return convertView;
    }
}
