package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/26/17.
 */

public class ActivitySignUpPageTwo extends AppCompatActivity implements View.OnClickListener{
    Spinner professionSp, industrySp, specialitySp;
    LinearLayout specialityLinear;
    TextView industryLoading;
    Button proceedBtn;
    String [] professions = {"Employee","Freelancer","Specialist","Student"};
    ArrayAdapter<String> adapter;
    String url;
    HashMap<String,String> serverRequestData, userDataFromSignUpPageHashmap, userdataFromServerHashmap;
    ArrayList<HashMap<String,String>> userDataFromSignUpPageArrayList, userDataFromSeverArraylist;
    Intent intent;
    HandleJsonDataFromServer handleJsonDataFromServer;
    String userId,industry,speciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page_two);
        overridePendingTransition(0, 0);

        intent = getIntent();
        userDataFromSignUpPageArrayList =
                (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("userDataArraylist");

        serverRequestData = new HashMap<>();
        userDataFromSignUpPageHashmap = userDataFromSignUpPageArrayList.get(0);
        userId = userDataFromSignUpPageHashmap.get("user_id");
        industry = userDataFromSignUpPageHashmap.get("industry");
        speciality = userDataFromSignUpPageHashmap.get("speciality");

        professionSp = (Spinner) findViewById(R.id.profession);
        industrySp = (Spinner) findViewById(R.id.industry);
        specialitySp = (Spinner) findViewById(R.id.speciality);
        proceedBtn = (Button) findViewById(R.id.proceed_btn);

        specialityLinear = (LinearLayout) findViewById(R.id.speciality_linear);

        industryLoading = (TextView) findViewById(R.id.industryLoading);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,professions);

        professionSp.setAdapter(adapter);

        //set industry spinner array
        handleJsonDataFromServer = new HandleJsonDataFromServer(industry);
        userDataFromSeverArraylist = handleJsonDataFromServer.signupPageSpinnerIndustryData();
        ArrayList<String> industryArrayListData = new ArrayList<String>();

        for(int i=0; i<userDataFromSeverArraylist.size();i++){
             userdataFromServerHashmap = userDataFromSeverArraylist.get(i);
             industryArrayListData.add(userdataFromServerHashmap.get("industry_name"));
        }

        adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_list_item_1,
                            industryArrayListData);
        industrySp.setAdapter(adapter);
        industryLoading.setVisibility(View.GONE);
        industrySp.setVisibility(View.VISIBLE);

        industrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                userdataFromServerHashmap = userDataFromSeverArraylist.get(position);
                String  industryID = userdataFromServerHashmap.get("industry_id");

                Log.d("hapa",""+position);
                Log.d("hapa",speciality);

                //set speciality spinner array
                handleJsonDataFromServer = new HandleJsonDataFromServer(speciality);
                userDataFromSeverArraylist = handleJsonDataFromServer.signupPageSpinnerSpecialityData(industryID);

                if(userDataFromSeverArraylist == null){
                    //do nothing
                }else {
                    ArrayList<String> industryArrayListData = new ArrayList<String>();

                    for (int i = 0; i < userDataFromSeverArraylist.size(); i++) {
                        userdataFromServerHashmap = userDataFromSeverArraylist.get(i);
                        industryArrayListData.add(userdataFromServerHashmap.get("speciality_name"));
                    }

                    adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_list_item_1,
                            industryArrayListData);
                    specialitySp.setAdapter(adapter);
                    specialityLinear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        proceedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.proceed_btn:
                intent = new Intent(ActivitySignUpPageTwo.this,ActivitySignupPageThree.class);
                intent.putExtra("signupPageArraylist",userDataFromSignUpPageArrayList);
                startActivity(intent);
                break;
        }
    }
}
