package com.example.cilo.codevated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/1/17.
 */

public class ActivityInterestConcepts extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg,interestIcon;
    TextView interestTitle,notifyCount;;
    ListView listView;
    Button allConceptsBtn;
    Intent intent;
    Common common;
    HashMap<String,String> dataFromConceptPageHashmap, resquestDataFromServer;
    String url;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_concepts);
        overridePendingTransition(0, 0);

        common = new Common(this);
        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();
        intent = getIntent();

        dataFromConceptPageHashmap = (HashMap<String, String>) intent.getSerializableExtra("dataSelectedHashmap");

        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        postConceptImg = (ImageView) toolbar.findViewById(R.id.post_concept_icon);
        logoImg = (ImageView) toolbar.findViewById(R.id.logo_icon);
        notifyImg = (ImageView) toolbar.findViewById(R.id.notify_icon);
        userImg = (ImageView) toolbar.findViewById(R.id.user_icon);
        notifyCount = (TextView) toolbar.findViewById(R.id.notify_count);

        setSupportActionBar(toolbar);

        interestIcon = (ImageView) findViewById(R.id.interest_icon);
        interestTitle = (TextView) findViewById(R.id.interest_title);
        listView = (ListView) findViewById(R.id.listview);
        allConceptsBtn = (Button) findViewById(R.id.all_concepts_btn);

        interestTitle.setText(dataFromConceptPageHashmap.get("cartegory_name"));
        Picasso.with(this).load(dataFromConceptPageHashmap.get("cartegory_icon")).into(interestIcon);

        url = "/getInterestPosts.php";
        resquestDataFromServer = new HashMap<>();
        resquestDataFromServer.put("user_id",""+user.userId);
        resquestDataFromServer.put("interest_id",dataFromConceptPageHashmap.get("interest_id"));

        new GetDataFromServer(resquestDataFromServer, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.getPosts();

                    CustomListviewInterestConceptsItem customListviewInterestConceptsItem =
                            new CustomListviewInterestConceptsItem(getBaseContext(),dataFromServerArraylist);

                    listView.setAdapter(customListviewInterestConceptsItem);
                }
            }
        }).execute();

        menuImg.setOnClickListener(this);
        postConceptImg.setOnClickListener(this);
        logoImg.setOnClickListener(this);
        notifyImg.setOnClickListener(this);
        userImg.setOnClickListener(this);
        allConceptsBtn.setOnClickListener(this);
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
            case R.id.post_concept_icon:
                intent = new Intent(getApplicationContext(),ActivityPostConcept.class);
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
            case R.id.all_concepts_btn:
                finish();
                break;
        }
    }
}
