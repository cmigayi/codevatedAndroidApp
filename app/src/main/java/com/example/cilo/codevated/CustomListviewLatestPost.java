package com.example.cilo.codevated;

import android.content.Context;
import android.support.annotation.NonNull;
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
 * Created by cilo on 4/20/17.
 */

public class CustomListviewLatestPost extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView title,viewTv,bottomContent;
    ImageView profImg,cartegoryIcon;

    public CustomListviewLatestPost(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_listview_latest_post, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_latest_post,null);
        title = (TextView) convertView.findViewById(R.id.title);
        viewTv = (TextView) convertView.findViewById(R.id.view);
        bottomContent = (TextView) convertView.findViewById(R.id.bottom_content);
        profImg = (ImageView) convertView.findViewById(R.id.prof_img);
        cartegoryIcon = (ImageView) convertView.findViewById(R.id.cartegory_icon);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);
        title.setText(hashMap.get("name"));
        viewTv.setText(hashMap.get("views"));
        bottomContent.setText("Content-type:"+hashMap.get("content_type")
                +", Resources:Attached, Posted by "+hashMap.get("username")+" | "+hashMap.get("posted_date"));

        if(hashMap.get("profile_img").equals("n") || hashMap.get("profile_img").equals("")){

        }else{
            Picasso.with(context).load(hashMap.get("profile_img")).transform(new CircleTransform()).into(profImg);
        }
        Picasso.with(context).load(hashMap.get("cartegory_icon")).into(cartegoryIcon);

        return convertView;
    }
}
