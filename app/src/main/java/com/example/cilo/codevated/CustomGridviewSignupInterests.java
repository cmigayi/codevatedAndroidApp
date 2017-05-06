package com.example.cilo.codevated;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/27/17.
 */

public class CustomGridviewSignupInterests extends ArrayAdapter implements View.OnClickListener{
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    ViewHolder viewHolder;
    String interestID;
    int viewPosition;

    public CustomGridviewSignupInterests(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_gridview_signup_interests, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_gridview_signup_interests,null);

            viewHolder = new ViewHolder();

            viewHolder.titleText = (TextView) convertView.findViewById(R.id.interest_title);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map = arrayList.get(position);

        interestID = map.get("interest_id");
        viewPosition = position;

        viewHolder.titleText.setText(map.get("cartegory_name"));
        Picasso.with(context).load(map.get("cartegory_icon")).into(viewHolder.imageView);

        viewHolder.checkBox.setOnClickListener(this);
        viewHolder.checkBox.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap = arrayList.get(pos);

        switch(v.getId()){
            case R.id.interest_title:

                break;
        }
    }

    static class ViewHolder{

        TextView titleText;
        ImageView imageView;
        CheckBox checkBox;
    }
}
