package com.example.cilo.codevated;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    String items = "";
    private int addInterestState = 0;

    LocalUserStorage localUserStorage;
    User user;

    Common common;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    LocaluserInterestDatabase localuserInterestDatabase;

    public CustomGridviewSignupInterests(Context context, ArrayList<HashMap<String,String>> arrayList) {
        super(context, R.layout.custom_gridview_signup_interests, arrayList);

        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        common = new Common(context);

        localUserStorage = new LocalUserStorage(context);
        user = localUserStorage.getSignedinUser();

        localuserInterestDatabase = new LocaluserInterestDatabase(context,"userInterests",null,1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_gridview_signup_interests,null);

            viewHolder = new ViewHolder();

            viewHolder.titleText = (TextView) convertView.findViewById(R.id.interest_title);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.addBtn = (Button) convertView.findViewById(R.id.add);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map = arrayList.get(position);

        interestID = map.get("interest_id");
        viewPosition = position;

        viewHolder.titleText.setText(map.get("cartegory_name"));
        Picasso.with(context).load(map.get("cartegory_icon")).into(viewHolder.imageView);

//        addInterestState = Integer.parseInt(map.get("status"));
//        Log.d("cilo105",""+addInterestState);
//        if(addInterestState == 1){
//            viewHolder.addBtn.setText("- Remove");
//            viewHolder.addBtn.setTextColor(Color.parseColor("#000000"));
//            viewHolder.addBtn.setBackgroundColor(Color.parseColor("#B5FFAA"));
//        }

        viewHolder.addBtn.setOnClickListener(this);
        viewHolder.addBtn.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap = arrayList.get(pos);

        switch(v.getId()){
            case R.id.add:

                viewHolder.addBtn = (Button) v.findViewById(v.getId());

                if(addInterestState == 0){

                    url="/addUserInterest.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("interest_id",hashmap.get("interest_id"));

                    new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerState = handleJsonDataFromServer.checkIfUserInterestState();

                                Log.d("cilo1012+",""+dataFromServerState);

                                if(dataFromServerState == -1){

                                }else{

                                    if(dataFromServerState == 1) {
                                        addInterestState = 1;
                                        viewHolder.addBtn.setText("- Remove");
                                        viewHolder.addBtn.setTextColor(Color.parseColor("#000000"));
                                        viewHolder.addBtn.setBackgroundColor(Color.parseColor("#B5FFAA"));
                                    }else{
                                        addInterestState = 0;
                                    }
                                }
                            }
                        }
                    }).execute();

                }else{

                    url="/deleteUserInterest.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("interest_id",hashmap.get("interest_id"));

                    new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerState = handleJsonDataFromServer.checkIfUserInterestState();

                                Log.d("cilo1012-",""+dataFromServerState);

                                if(dataFromServerState == -1){

                                }else{

                                    if(dataFromServerState == 1) {
                                        addInterestState = 0;
                                        viewHolder.addBtn.setText("+ Add");
                                        viewHolder.addBtn.setTextColor(Color.parseColor("#ffffff"));
                                        viewHolder.addBtn.setBackgroundColor(Color.parseColor("#54a846"));
                                    }else{
                                        addInterestState = 1;
                                    }

                                }
                            }
                        }
                    }).execute();
                }
                Log.d("cilo1012",""+addInterestState);

                break;
        }
    }

    static class ViewHolder{

        TextView titleText;
        ImageView imageView;
        Button addBtn;
    }
}
