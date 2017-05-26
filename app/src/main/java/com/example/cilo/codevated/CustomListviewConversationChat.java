package com.example.cilo.codevated;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/24/17.
 */

public class CustomListviewConversationChat extends ArrayAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView messageTv, postedDateTv,usernameTv;

    LinearLayout linearLayout;

    HashMap<String,String> hash;

    LocalUserStorage localUserStorage;
    User user;

    public CustomListviewConversationChat(Context context, ArrayList<HashMap<String,String>> arrayList,
                                          HashMap<String,String> hash) {

        super(context, R.layout.custom_listview_conversation_chat, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        this.hash = hash;

        localUserStorage = new LocalUserStorage(context);
        user = localUserStorage.getSignedinUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_listview_conversation_chat,null);
        messageTv = (TextView) convertView.findViewById(R.id.message);
        postedDateTv = (TextView) convertView.findViewById(R.id.posted_date);
        usernameTv = (TextView) convertView.findViewById(R.id.username);
        linearLayout = (LinearLayout) convertView.findViewById(R.id.linear1);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);

        if(hashMap.get("respondent").equals(""+user.userId)){
            usernameTv.setText(hash.get("username"));

        }else{
            linearLayout.setBackgroundColor(Color.parseColor("#B3DB88"));
            usernameTv.setText("You");
            usernameTv.setTextColor(Color.parseColor("#B3DB88"));
        }
        messageTv.setText(hashMap.get("message"));
        postedDateTv.setText(hashMap.get("posted_date"));

        return convertView;
    }
}
