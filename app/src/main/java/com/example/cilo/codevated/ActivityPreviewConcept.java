package com.example.cilo.codevated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/2/17.
 */

public class ActivityPreviewConcept extends AppCompatActivity implements View.OnClickListener{
    TextView conceptContent,conceptTitle,totalTitleContent,totalContent,notifyCount,dataPosted;
    ScrollView scrollView;
    Button postBtn;

    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg;
    TabLayout tabLayout;
    ViewPager viewPager;
    Intent intent;

    LocalConceptDatabase localConceptDatabase;
    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;

    Concept concept;
    String url,titleStr,contentStr;

    HandleJsonDataFromServer handleJsonDataFromServer;
    int dataFromServerState;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_concept);
        overridePendingTransition(0, 0);

        localConceptDatabase = new LocalConceptDatabase(this,"conceptDB",null,1);
        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        postConceptImg = (ImageView) toolbar.findViewById(R.id.post_concept_icon);
        logoImg = (ImageView) toolbar.findViewById(R.id.logo_icon);
        notifyImg = (ImageView) toolbar.findViewById(R.id.notify_icon);
        userImg = (ImageView) toolbar.findViewById(R.id.user_icon);
        notifyCount = (TextView) toolbar.findViewById(R.id.notify_count);

        requestFromServerHashmap = new HashMap<>();

        totalTitleContent = (TextView) findViewById(R.id.total_title_content);
        totalContent = (TextView) findViewById(R.id.total_content);

        conceptTitle = (TextView) findViewById(R.id.concept_title);
        conceptContent = (TextView) findViewById(R.id.concept_content);
        dataPosted = (TextView) findViewById(R.id.data_posted);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        postBtn = (Button) findViewById(R.id.post_btn);

        concept = localConceptDatabase.getConceptData();

        titleStr = concept.conceptTitle;
        contentStr = concept.conceptContent;

        conceptTitle.setText(titleStr);
        conceptContent.setText(contentStr);

        totalTitleContent.setText(titleStr.length()+" chars");
        totalContent.setText(contentStr.length()+" chars");

        postBtn.setOnClickListener(this);

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
            case R.id.post_btn:
                url = "/postConcept.php";
                requestFromServerHashmap.put("user_id",""+user.userId);
                requestFromServerHashmap.put("title",titleStr);
                requestFromServerHashmap.put("content",contentStr);
                requestFromServerHashmap.put("interest_id",""+6);

                new SendDataToServer(requestFromServerHashmap, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            dataFromServerState = handleJsonDataFromServer.checkIfConceptIsPosted();

                            Log.d("cilo122",""+dataFromServerState);

                            if(dataFromServerState == 1){
                                Log.d("cilo122","Successfully posted");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dataPosted.setVisibility(View.VISIBLE);
                                        scrollView.setVisibility(View.GONE);
                                    }
                                },1000);

                            }else{
                                Log.d("cilo122","Something went terribly wrong "+dataFromServerState);
                            }

                        }
                    }
                }).execute();

                break;
        }
    }
}
