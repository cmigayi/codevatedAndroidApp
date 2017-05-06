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
 * Created by cilo on 4/23/17.
 */

public class CustomGridviewAllMentors extends ArrayAdapter implements View.OnClickListener{
    ArrayList<HashMap<String,String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    ViewHolder viewHolder;
    private int addMentorstate;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    public CustomGridviewAllMentors(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_gridview_all_mentors, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        localUserStorage = new LocalUserStorage(context);
        user = localUserStorage.getSignedinUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_gridview_all_mentors,null);
            viewHolder = new ViewHolder();

            viewHolder.titleText = (TextView) convertView.findViewById(R.id.username);
            viewHolder.addMentorBtn = (Button) convertView.findViewById(R.id.add_mentor);
            viewHolder.loadingLayout = (LinearLayout) convertView.findViewById(R.id.loading_linear);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map = new HashMap<>();
        map = arrayList.get(position);
        viewHolder.titleText.setText(map.get("username"));

        addMentorstate = Integer.parseInt(map.get("status"));
        if(addMentorstate == 1){
            viewHolder.addMentorBtn.setText("- Remove mentor");
            viewHolder.addMentorBtn.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        viewHolder.addMentorBtn.setOnClickListener(this);
        viewHolder.addMentorBtn.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(pos);

        switch (v.getId()){
            case R.id.add_mentor:
                viewHolder.addMentorBtn = (Button) v.findViewById(v.getId());

                if(addMentorstate == 0){

                    url = "/addmentor.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("mentor_id",hashMap.get("user_id"));

                    new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerState = handleJsonDataFromServer.checkIfMentorState();

                                if(dataFromServerState == -1){

                                }else{
                                    addMentorstate = 1;
                                    viewHolder.addMentorBtn.setText("- Remove mentor");
                                    viewHolder.addMentorBtn.setBackgroundColor(Color.parseColor("#eeeeee"));

                                    Log.d("cilo91",dataFromServerState+" - "+addMentorstate);
                                }
                            }
                        }
                    }).execute();

                }else{

                    url = "/deleteMentor.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("mentor_id",hashMap.get("user_id"));

                    new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerState = handleJsonDataFromServer.checkIfMentorState();

                                if(dataFromServerState == -1){

                                }else{
                                    addMentorstate = 0;
                                    viewHolder.addMentorBtn.setText("+ Add mentor");
                                    viewHolder.addMentorBtn.setBackgroundColor(Color.parseColor("#cccccc"));

                                    Log.d("cilo91",dataFromServerState+" - "+addMentorstate);
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
        Button addMentorBtn;
        LinearLayout loadingLayout;
    }
}
