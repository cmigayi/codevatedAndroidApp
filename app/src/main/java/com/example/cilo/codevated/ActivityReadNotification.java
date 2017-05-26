package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/24/17.
 */

public class ActivityReadNotification extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg;
    TextView notifyCount,title,message;
    Intent intent;
    HashMap<String,String> hashMap;

    Common common;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;
    int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_notification);
        overridePendingTransition(0,0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        postConceptImg = (ImageView) toolbar.findViewById(R.id.post_concept_icon);
        logoImg = (ImageView) toolbar.findViewById(R.id.logo_icon);
        notifyImg = (ImageView) toolbar.findViewById(R.id.notify_icon);
        userImg = (ImageView) toolbar.findViewById(R.id.user_icon);
        notifyCount = (TextView) toolbar.findViewById(R.id.notify_count);

        common = new Common(this);
        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        title = (TextView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.message);

        intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("dataFromAnotherPage");

        title.setText(hashMap.get("title"));
        message.setText(hashMap.get("message"));

        url = "/markNotificationAsRead.php";
        requestFromServerHashmap = new HashMap<>();
        requestFromServerHashmap.put("user_id",""+user.userId);
        requestFromServerHashmap.put("notification_id",hashMap.get("notification_id"));

        new SendDataToServer(requestFromServerHashmap, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    state = handleJsonDataFromServer.markNotificationAsRead();

                    Log.d("cilo120",""+state);

                }
            }
        }).execute();

        menuImg.setOnClickListener(this);
        postConceptImg.setOnClickListener(this);
        logoImg.setOnClickListener(this);
        notifyImg.setOnClickListener(this);
        userImg.setOnClickListener(this);
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
        }
    }
}
