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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/26/17.
 */

public class ActivityProfileAccount extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg;
    EditText oldPwdEt,newPwdEt,confirmNewPwdEt,usernameEt;
    Button changePwdBtn,changeUsernameBtn,deleteAccountBtn;
    TextView notifyCount;
    Intent intent;
    Common common;
    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap,dataFromServerHashmap;

    Concept concept;
    String url,titleStr,contentStr;

    HandleJsonDataFromServer handleJsonDataFromServer;
    int dataFromServerState;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account);

        overridePendingTransition(0,0);

        common = new Common(this);
        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        postConceptImg = (ImageView) toolbar.findViewById(R.id.post_concept_icon);
        logoImg = (ImageView) toolbar.findViewById(R.id.logo_icon);
        notifyImg = (ImageView) toolbar.findViewById(R.id.notify_icon);
        userImg = (ImageView) toolbar.findViewById(R.id.user_icon);
        notifyCount = (TextView) toolbar.findViewById(R.id.notify_count);

        logoImg.setImageResource(R.drawable.home);

        menuImg.setOnClickListener(this);
        postConceptImg.setOnClickListener(this);
        logoImg.setOnClickListener(this);
        notifyImg.setOnClickListener(this);
        userImg.setOnClickListener(this);

        setSupportActionBar(toolbar);

        oldPwdEt = (EditText) findViewById(R.id.old_pwd);
        newPwdEt = (EditText) findViewById(R.id.new_pwd);
        confirmNewPwdEt = (EditText) findViewById(R.id.confirm_new_pwd);
        usernameEt = (EditText) findViewById(R.id.username);

        changePwdBtn = (Button) findViewById(R.id.change_pwd_btn);
        changeUsernameBtn = (Button) findViewById(R.id.change_username_btn);
        deleteAccountBtn = (Button) findViewById(R.id.delete_account_btn);

        changePwdBtn.setOnClickListener(this);
        changeUsernameBtn.setOnClickListener(this);
        deleteAccountBtn.setOnClickListener(this);
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
            case R.id.change_pwd_btn:
                String newPwdStr = newPwdEt.getText().toString();
                String oldPwdStr = oldPwdEt.getText().toString();
                String confirmNewPwdStr = confirmNewPwdEt.getText().toString();

                if(oldPwdStr.length()>0 && newPwdStr.length()>0 && confirmNewPwdStr.length()>0){

                    if(oldPwdStr.equals(user.password)){

                        if(newPwdStr.equals(confirmNewPwdStr)){
                            url = "/changePassword.php";
                            requestFromServerHashmap = new HashMap<>();
                            requestFromServerHashmap.put("user_id",""+user.userId);
                            requestFromServerHashmap.put("pass",newPwdStr);

                            new SendDataToServer(requestFromServerHashmap, url, new UrlCallBack() {
                                @Override
                                public void done(String response) {
                                    if(response == null){

                                    }else{
                                        handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                        dataFromServerArraylist = handleJsonDataFromServer.changePassword();

                                        if(dataFromServerArraylist == null){

                                        }else{
                                            dataFromServerHashmap = new HashMap<String, String>();
                                            dataFromServerHashmap = dataFromServerArraylist.get(0);

                                            user.setPassword(dataFromServerHashmap.get("password"));
                                            localUserStorage.StorePassword(user);
                                        }
                                    }
                                }
                            }).execute();

                        }else{
                            //Your new password do not match
                        }
                    }else{
                        //You entered a wrong old password
                    }

                }else{
                    //all fields must be filled
                }

                break;
            case R.id.change_username_btn:
                String usernameStr = usernameEt.getText().toString();
                if(usernameStr.length() > 0){
                    url = "/changeUsername.php";
                    requestFromServerHashmap = new HashMap<>();
                    requestFromServerHashmap.put("user_id",""+user.userId);
                    requestFromServerHashmap.put("username",usernameStr);

                    new SendDataToServer(requestFromServerHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String response) {
                            if(response == null){

                            }else{
                                handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                dataFromServerArraylist = handleJsonDataFromServer.changeUsername();

                                if(dataFromServerArraylist == null){

                                }else {
                                    dataFromServerHashmap = new HashMap<String, String>();
                                    dataFromServerHashmap = dataFromServerArraylist.get(0);

                                    user.setPassword(dataFromServerHashmap.get("username"));
                                    localUserStorage.StoreUsername(user);
                                }
                            }
                        }
                    }).execute();

                }else{
                    //No username provided
                }
                break;
            case R.id.delete_account_btn:
                url = "/deleteAccount.php";
                requestFromServerHashmap = new HashMap<>();
                requestFromServerHashmap.put("user_id",""+user.userId);

                new SendDataToServer(requestFromServerHashmap, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            dataFromServerArraylist = handleJsonDataFromServer.deleteAccount();

                            if(dataFromServerArraylist == null){

                            }else {
                                dataFromServerHashmap = dataFromServerArraylist.get(0);

                                if(dataFromServerHashmap.get("status").equals("true")){
                                    localUserStorage.clearuserData();
                                    startActivity(new Intent(getApplicationContext(),ActivityMain.class));
                                }
                            }
                        }
                    }
                }).execute();

                break;
        }
    }

}
