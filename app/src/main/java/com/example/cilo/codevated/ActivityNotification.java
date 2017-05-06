package com.example.cilo.codevated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/23/17.
 */

public class ActivityNotification extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg;
    TextView notifyCount;
    ListView listView;
    Intent intent;
    Common common;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        overridePendingTransition(0, 0);

        common = new Common(this);
        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        postConceptImg = (ImageView) toolbar.findViewById(R.id.post_concept_icon);
        logoImg = (ImageView) toolbar.findViewById(R.id.logo_icon);
        userImg = (ImageView) toolbar.findViewById(R.id.user_icon);
        notifyImg = (ImageView) toolbar.findViewById(R.id.notify_icon);
        notifyCount = (TextView) toolbar.findViewById(R.id.notify_count);

        notifyImg.setImageResource(R.drawable.notify_grey);
        notifyCount.setVisibility(View.GONE);
        logoImg.setImageResource(R.drawable.home);

        menuImg.setOnClickListener(this);
        postConceptImg.setOnClickListener(this);
        logoImg.setOnClickListener(this);
        userImg.setOnClickListener(this);

        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listview);
        url = "/getNotifications.php";
        requestFromServerHashmap = new HashMap<>();
        requestFromServerHashmap.put("user_id",""+user.userId);
        requestFromServerHashmap.put("data","all");
        new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.getAllNotifications();

                    if(dataFromServerArraylist == null){

                    }else{
                        CustomListviewNotifications customListviewNotifications =
                                new CustomListviewNotifications(getBaseContext(),dataFromServerArraylist);
                        listView.setAdapter(customListviewNotifications);
                    }
                }
            }
        }).execute();
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
            case R.id.post_concept_icon:
                intent = new Intent(getApplicationContext(),ActivityPostConcept.class);
                startActivity(intent);
                break;
            case R.id.user_icon:
                intent = new Intent(getApplicationContext(),ActivityUserProfile.class);
                startActivity(intent);
                break;
        }
    }
}
