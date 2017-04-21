package com.example.cilo.codevated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cilo on 4/21/17.
 */

public class CustomListviewMoreMenuItems extends ArrayAdapter {
    String [] items;
    int [] imgs;
    Context context;
    LayoutInflater layoutInflater;

    public CustomListviewMoreMenuItems(Context context, String[] items, int [] imgs) {
        super(context, R.layout.custom_listview_more_menu_items, items);
        this.items = items;
        this.imgs = imgs;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_more_menu_items,null);
        TextView menuTitle = (TextView) convertView.findViewById(R.id.menu_title);
        ImageView menuIcon = (ImageView) convertView.findViewById(R.id.menu_icon);
        menuTitle.setText(items[position]);
        menuIcon.setImageResource(imgs[position]);
        return convertView;
    }
}
