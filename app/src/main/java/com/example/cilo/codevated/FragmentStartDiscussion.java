package com.example.cilo.codevated;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/8/17.
 */

public class FragmentStartDiscussion extends Fragment implements View.OnClickListener {
    EditText topicEt;
    TextView totalCharTv;
    Button postBtn,successBtn;
    int num,allowedNum = 0;
    ProgressBar progressBarOne;

    LocalUserStorage localUserStorage;
    User user;
    String url,postedStatus,topicStr;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    HashMap<String,String> dataSelectedHashMap,requestDataFromServer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_discussion,null);

        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        topicEt = (EditText) view.findViewById(R.id.topic);
        totalCharTv = (TextView) view.findViewById(R.id.total_char);
        postBtn = (Button) view.findViewById(R.id.post_btn);
        successBtn = (Button) view.findViewById(R.id.success_btn);

        topicEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                num = topicEt.getText().toString().length();
                if(num > 100){
                    num = 100 - num;
                    totalCharTv.setTextColor(Color.parseColor("#cc2118"));
                }else{
                    allowedNum = allowedNum + 1;
                    totalCharTv.setTextColor(Color.parseColor("#000000"));
                    Log.d("cilo458",""+allowedNum);
                }

                totalCharTv.setText("Char: "+num);
                return false;
            }
        });

        postBtn.setOnClickListener(this);
        successBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_btn:
                topicStr = topicEt.getText().toString();

                url = "/postDiscussion.php";
                requestDataFromServer = new HashMap<>();
                requestDataFromServer.put("user_id",""+user.userId);
                requestDataFromServer.put("topic",topicStr);

                new SendDataToServer(requestDataFromServer, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            postedStatus = handleJsonDataFromServer.checkIfDiscussionIsPosted();

                            if(postedStatus == null){

                            }else{
                                if(postedStatus.equals("success")){

                                    postBtn.setVisibility(View.GONE);
                                    successBtn.setVisibility(View.VISIBLE);
                                    topicEt.setText("");

                                }else{

                                }
                            }
                        }
                    }
                }).execute();

                break;

            case R.id.success_btn:
                    postBtn.setVisibility(View.VISIBLE);
                    successBtn.setVisibility(View.GONE);
                break;
        }
    }
}
