package com.example.cilo.codevated;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/7/17.
 */

public class CustomGridviewAllMentees extends ArrayAdapter implements View.OnClickListener{
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    ViewHolder viewHolder;
    private int addMenteeState;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    public CustomGridviewAllMentees(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_gridview_all_mentees, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        localUserStorage = new LocalUserStorage(context);
        user = localUserStorage.getSignedinUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_gridview_all_mentees,null);
            viewHolder = new ViewHolder();

            viewHolder.titleText = (TextView) convertView.findViewById(R.id.username);
            viewHolder.addMenteeBtn = (Button) convertView.findViewById(R.id.add_mentee);
            viewHolder.loadingLayout = (LinearLayout) convertView.findViewById(R.id.loading_linear);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map = new HashMap<>();
        map = arrayList.get(position);
        viewHolder.titleText.setText(map.get("username"));

        addMenteeState = Integer.parseInt(map.get("status"));
        if(addMenteeState == 1){
            viewHolder.addMenteeBtn.setText("- Remove mentee");
            viewHolder.addMenteeBtn.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        viewHolder.addMenteeBtn.setOnClickListener(this);
        viewHolder.addMenteeBtn.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(pos);

        switch (v.getId()){
            case R.id.add_mentee:
                viewHolder.addMenteeBtn = (Button) v.findViewById(v.getId());

                if(addMenteeState == 0){

                    url = "/addmentee.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("mentee_id",hashMap.get("user_id"));

                    new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerState = handleJsonDataFromServer.checkIfMenteeState();

                                if(dataFromServerState == -1){

                                }else{
                                    addMenteeState = 1;
                                    viewHolder.addMenteeBtn.setText("- Remove mentee");
                                    viewHolder.addMenteeBtn.setBackgroundColor(Color.parseColor("#eeeeee"));

                                    Log.d("cilo91",dataFromServerState+" - "+addMenteeState);
                                }
                            }
                        }
                    }).execute();

                }else{

                    url = "/deleteMentee.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("mentee_id",hashMap.get("user_id"));

                    new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerState = handleJsonDataFromServer.checkIfMentorState();

                                if(dataFromServerState == -1){

                                }else{
                                    addMenteeState = 0;
                                    viewHolder.addMenteeBtn.setText("+ Add mentee");
                                    viewHolder.addMenteeBtn.setBackgroundColor(Color.parseColor("#cccccc"));

                                    Log.d("cilo91",dataFromServerState+" - "+addMenteeState);
                                }
                            }
                        }
                    }).execute();

                }



                break;
        }
    }

    class ViewHolder{
        TextView titleText;
        Button addMenteeBtn;
        LinearLayout loadingLayout;
    }
}