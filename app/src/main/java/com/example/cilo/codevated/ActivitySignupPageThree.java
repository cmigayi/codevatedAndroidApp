package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/27/17.
 */

public class ActivitySignupPageThree extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    Button proceedBtn;

    Intent intent;
    HashMap<String,String> serverRequestData, userDataFromSignUpPageHashmap, userdataFromServerHashmap;
    ArrayList<HashMap<String,String>> userDataFromSignUpPageArrayList, userDataFromSeverArraylist;
    HandleJsonDataFromServer handleJsonDataFromServer;
    User user;
    LocalUserStorage localUserStorage;

    String items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page_three);
        overridePendingTransition(0, 0);

        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        gridView = (GridView) findViewById(R.id.gridView);
        proceedBtn = (Button) findViewById(R.id.proceed_btn);

        String url = "/getInterests.php";
        serverRequestData = new HashMap<>();
        serverRequestData.put("user_id",""+user.userId);

        new GetDataFromServer(serverRequestData, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    userDataFromSeverArraylist = handleJsonDataFromServer.signupPageInterestGridViewData();

                    CustomGridviewSignupInterests customGridviewSignupInterests =
                            new CustomGridviewSignupInterests(getBaseContext(),userDataFromSeverArraylist);
                    gridView.setAdapter(customGridviewSignupInterests);
                }
            }
        }).execute();

        proceedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.proceed_btn:
                intent = new Intent(getApplicationContext(),ActivitySignupPageFinish.class);
                startActivity(intent);
                break;
        }
    }
}
