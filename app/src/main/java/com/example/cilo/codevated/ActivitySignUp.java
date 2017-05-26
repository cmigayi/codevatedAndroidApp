package com.example.cilo.codevated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/24/17.
 */

public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener{
    Button signinBtn,signupBtn;
    Intent intent;
    EditText usernameEt,emailEt,passEt,pass2Et;
    RadioGroup genderRb;
    RadioButton selectedGender;
    TextView errorTv;

    String usernameStr,emailStr,genderStr,passStr,errorStr;

    LinearLayout loadingLinear,loadingLinearCheckInputs,signupLinear;
    RelativeLayout signinRelative;

    ArrayList<HashMap<String,String>> userDataArrayList;
    HandleJsonDataFromServer handleJsonDataFromServer;
    String url;
    HashMap<String,String> serverRequestData;

    User user;
    LocalUserStorage localUserStorage;
    LocalConceptDatabase localConceptDatabase;

    int checkFieldState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        overridePendingTransition(0, 0);

        localUserStorage = new LocalUserStorage(this);
        localConceptDatabase = new LocalConceptDatabase(this,"conceptDB",null,1);

        errorTv = (TextView) findViewById(R.id.errors);
        usernameEt = (EditText) findViewById(R.id.username);
        emailEt = (EditText) findViewById(R.id.email);
        genderRb = (RadioGroup) findViewById(R.id.gender);
        passEt = (EditText) findViewById(R.id.password);
        pass2Et = (EditText) findViewById(R.id.password2);

        signupBtn = (Button) findViewById(R.id.signup);
        signinBtn = (Button) findViewById(R.id.signin);

        loadingLinear = (LinearLayout) findViewById(R.id.loading_linear);
        loadingLinearCheckInputs = (LinearLayout) findViewById(R.id.loading_linear_check_inputs);
        signupLinear = (LinearLayout) findViewById(R.id.linear_signup);
        signinRelative = (RelativeLayout) findViewById(R.id.relative_signin);

        usernameEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                url = "/checkIfDataExists.php";
                serverRequestData = new HashMap<String, String>();
                serverRequestData.put("fieldType","username");
                serverRequestData.put("username",usernameEt.getText().toString());
                Log.d("username",usernameEt.getText().toString());
                loadingLinearCheckInputs.setVisibility(View.VISIBLE);
                errorTv.setVisibility(View.GONE);
                new GetDataFromServer(serverRequestData, url, new UrlCallBack() {
                    @Override
                    public void done(final String response) {
                        if(response == null){

                        }else{

                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            int checkFieldState = handleJsonDataFromServer.checkIfInputFieldExists();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingLinearCheckInputs.setVisibility(View.GONE);
                                    errorTv.setVisibility(View.VISIBLE);
                                }
                            },1000);

                            switch(checkFieldState){
                                case -1:
                                    errorTv.setText("Sorry, data did not process well. Try again!");
                                    errorTv.setTextColor(Color.parseColor("#cc2118"));
                                    break;
                                case 0:
                                    errorTv.setText("Sorry, that username is taken. Try again!");
                                    errorTv.setTextColor(Color.parseColor("#cc2118"));
                                    break;
                                case 1:
                                    errorTv.setText("Congrats, the username is not taken!");
                                    errorTv.setTextColor(Color.parseColor("#76DB51"));
                                    break;
                            }
                        }
                    }
                }).execute();
                return false;
            }
        });

        emailEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                url = "/checkIfDataExists.php";
                serverRequestData = new HashMap<String, String>();
                serverRequestData.put("fieldType","email");
                serverRequestData.put("email",emailEt.getText().toString());
                Log.d("username",emailEt.getText().toString());
                loadingLinearCheckInputs.setVisibility(View.VISIBLE);
                errorTv.setVisibility(View.GONE);
                new GetDataFromServer(serverRequestData, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            int checkFieldState = handleJsonDataFromServer.checkIfInputFieldExists();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingLinearCheckInputs.setVisibility(View.GONE);
                                    errorTv.setVisibility(View.VISIBLE);
                                }
                            },1000);

                            switch(checkFieldState){
                                case -1:
                                    errorTv.setText("Sorry, data did not process well. Try again!");
                                    errorTv.setTextColor(Color.parseColor("#cc2118"));
                                    break;
                                case 0:
                                    errorTv.setText("Sorry, that email is taken. Try again!");
                                    errorTv.setTextColor(Color.parseColor("#cc2118"));
                                    break;
                                case 1:
                                    errorTv.setText("Congrats, the email is not taken!");
                                    errorTv.setTextColor(Color.parseColor("#76DB51"));
                                    break;
                            }

                        }
                    }
                }).execute();

                return false;
            }
        });

        signupBtn.setOnClickListener(this);
        signinBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signin:
                intent = new Intent(getApplicationContext(),ActivityMain.class);
                startActivity(intent);
                break;
            case R.id.signup:
                errorStr = validateUserSignUpInput();

                if(errorStr == null){
                    HashMap<String,String> userHashmap = new HashMap<String, String>();
                    String url = "/signup.php";

                    userHashmap.put("username",usernameStr);
                    userHashmap.put("email",emailStr);
                    userHashmap.put("gender",genderStr);
                    userHashmap.put("pass",passStr);

                    loadingLinear.setVisibility(View.VISIBLE);
                    signinRelative.setVisibility(View.GONE);
                    signupLinear.setVisibility(View.GONE);

                    new SendDataToServer(userHashmap, url, new UrlCallBack() {
                        @Override
                        public void done(String result) {

                            if(result == null){
                                Log.d("cecil","no result");
                            }else{
                                loadingLinear.setVisibility(View.GONE);

                                handleJsonDataFromServer = new HandleJsonDataFromServer(result);
                                userDataArrayList = handleJsonDataFromServer.userContentFromServer();

                                if(userDataArrayList == null){
                                    signinRelative.setVisibility(View.VISIBLE);
                                    signupLinear.setVisibility(View.VISIBLE);

                                    errorTv.setText("Sorry, signup process was not complete!");
                                }else {

                                    user = new User(userDataArrayList);

                                    localUserStorage.StoreUserData(user);
                                    localConceptDatabase.insertData("","");

                                    Log.d("cecil", result);
                                    Intent intent = new Intent(ActivitySignUp.this, ActivitySignUpPageTwo.class);
                                    intent.putExtra("userDataArraylist", userDataArrayList);
                                    startActivity(intent);
                                }

                            }

                        }
                    }).execute();

                }else{
                    errorTv.setText(errorStr);
                }
                break;
        }
    }

    private String validateUserSignUpInput(){
        String errorState = null;
        selectedGender = (RadioButton) findViewById(genderRb.getCheckedRadioButtonId());

        if(usernameEt.getText().length() > 0){
            usernameStr = usernameEt.getText().toString();
        }else{
            errorState = "Username is empty!";
        }

        if(emailEt.getText().length() > 0){
            emailStr = emailEt.getText().toString();
        }else{
            errorState = "Email is empty!";
        }

        if(selectedGender.isChecked()) {
            genderStr = selectedGender.getText().toString();
        }

        if(passEt.getText().length() > 0 && pass2Et.getText().length() > 0){

            //check if password and confirm password match
            if(passEt.getText().toString().equals(pass2Et.getText().toString())){
                passStr = passEt.getText().toString();
            }else{
                errorState = "Password and confirm password should match!";
            }

        }else{
            errorState = "Password/confirm password is empty!";
        }

        return errorState;
    }
}
