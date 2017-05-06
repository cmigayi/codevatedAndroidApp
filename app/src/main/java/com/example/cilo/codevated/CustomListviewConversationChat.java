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

public class CustomListviewConversationChat extends ArrayAdapter {
    String [] items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewConversationChat(Context context, String[] items) {
        super(context, R.layout.custom_listview_conversation_chat, items);

        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_conversation_chat,null);
        TextView username = (TextView) convertView.findViewById(R.id.username);

        username.append(items[position]);
        return convertView;
    }
}
