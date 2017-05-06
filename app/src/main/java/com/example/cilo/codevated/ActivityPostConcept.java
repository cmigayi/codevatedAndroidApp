package com.example.cilo.codevated;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.transition.Visibility;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/21/17.
 */

public class ActivityPostConcept extends AppCompatActivity implements View.OnClickListener{
    EditText conceptContent,conceptTitle;
    TextView totalTitleContent,totalContent,notifyCount;
    Button previewPostBtn;

    String conceptInputContainer;

    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg;
    Intent intent;
    Common common;

    ProgressBar progressBarOne,progressBarTwo;

    int num,allowedNum = 0;

    LocalConceptDatabase localConceptDatabase;
    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_concept);
        overridePendingTransition(0, 0);

        common = new Common(this);
        localConceptDatabase = new LocalConceptDatabase(this,"conceptDB",null,1);
        Log.d("cilo45",""+localConceptDatabase.getData());
        ArrayList<String> arrayList = localConceptDatabase.getData();

        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        postConceptImg = (ImageView) toolbar.findViewById(R.id.post_concept_icon);
        logoImg = (ImageView) toolbar.findViewById(R.id.logo_icon);
        userImg = (ImageView) findViewById(R.id.user_icon);
        notifyImg = (ImageView) findViewById(R.id.notify_icon);
        notifyCount = (TextView) toolbar.findViewById(R.id.notify_count);

        postConceptImg.setImageResource(R.drawable.post_concept_grey);
        logoImg.setImageResource(R.drawable.home);

        totalTitleContent = (TextView) findViewById(R.id.total_title_content);
        totalContent = (TextView) findViewById(R.id.total_content);

        conceptTitle = (EditText) findViewById(R.id.concept_title);
        conceptContent = (EditText) findViewById(R.id.concept_content);
        previewPostBtn = (Button) findViewById(R.id.preview_post_btn);

        progressBarOne = (ProgressBar) findViewById(R.id.progressBarOne);
        progressBarTwo = (ProgressBar) findViewById(R.id.progressBarTwo);

        conceptTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                progressBarOne.setVisibility(View.VISIBLE);
                num = conceptTitle.getText().toString().length();
                if(num > 100){
                    num = 100 - num;
                    totalTitleContent.setTextColor(Color.parseColor("#cc2118"));
                }else{
                    allowedNum = allowedNum + 1;
                    Log.d("cilo458",""+allowedNum);
                    if(allowedNum > 20){
                        localConceptDatabase.updateTitleData(1,conceptTitle.getText().toString());
                        Log.d("cilo458",""+allowedNum);
                        allowedNum = 0;
                        Log.d("cilo45",""+localConceptDatabase.getData());
                    }
                    totalTitleContent.setTextColor(Color.parseColor("#4b88cc"));
                }

                totalTitleContent.setText("Char: "+num);
                return false;
            }
        });

        conceptContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                progressBarTwo.setVisibility(View.VISIBLE);
                num = conceptContent.getText().toString().length();
                if(num > 1000){
                    num = 1000 - num;
                    totalContent.setTextColor(Color.parseColor("#cc2118"));
                }else{
                    allowedNum = allowedNum + 1;
                    Log.d("cilo458",""+allowedNum);
                    if(allowedNum > 20){
                        localConceptDatabase.updateContentData(1,conceptContent.getText().toString());
                        Log.d("cilo458",""+allowedNum);
                        allowedNum = 0;
                        Log.d("cilo45",""+localConceptDatabase.getData());
                    }
                    totalContent.setTextColor(Color.parseColor("#4b88cc"));
                }
                totalContent.setText("Char: "+num);
                return false;
            }
        });

        conceptContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                common.inputMediaData();
                return false;
            }
        });

        menuImg.setOnClickListener(this);
        logoImg.setOnClickListener(this);
        previewPostBtn.setOnClickListener(this);
        userImg.setOnClickListener(this);
        notifyImg.setOnClickListener(this);

        setSupportActionBar(toolbar);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String item = intent.getStringExtra("data");
                    Log.d("cilo11111",item);
                    handleJsonDataFromServer = new HandleJsonDataFromServer(item);
                    dataFromServerState = handleJsonDataFromServer.getTotalUnreadNotifications();
                    if(dataFromServerState == -1){

                    }else{
                        if(dataFromServerState > 0){
                            notifyCount.setText(""+dataFromServerState);
                            notifyCount.setVisibility(View.VISIBLE);
                        }
                    }
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("notifications"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.menu_icon:
                common.moreMenuPopUp();
                break;
            case R.id.logo_icon:
                intent = new Intent(getApplicationContext(),ActivityHome.class);
                startActivity(intent);
                break;
            case  R.id.preview_post_btn:
                intent = new Intent(getApplicationContext(),ActivityPreviewConcept.class);
                startActivity(intent);
                break;
            case R.id.notify_icon:
                intent = new Intent(getApplicationContext(),ActivityNotification.class);
                startActivity(intent);
                break;
            case R.id.user_icon:
                intent = new Intent(getApplicationContext(),ActivityUserProfile.class);
                startActivity(intent);
                break;
        }
    }
}
