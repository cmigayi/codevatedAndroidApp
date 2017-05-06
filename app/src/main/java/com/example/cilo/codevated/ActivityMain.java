package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    EditText usernameOrEmailEt, passEt;
    String usernameOrEmailStr,passStr;
    Button signinBtn,signupBtn,forgotPwdBtn;
    Intent intent;
    HashMap<String,String> requestServerData;
    String inputValidationState,url;
    TextView errorTv;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    LocalUserStorage localUserStorage;
    User user;
    boolean loginStatus;
    LinearLayout loadingLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        localUserStorage = new LocalUserStorage(this);

        usernameOrEmailEt = (EditText) findViewById(R.id.username_or_email);
        passEt = (EditText) findViewById(R.id.password);

        errorTv = (TextView) findViewById(R.id.errors);
        signinBtn = (Button) findViewById(R.id.signin);
        signupBtn = (Button) findViewById(R.id.signup);
        forgotPwdBtn = (Button) findViewById(R.id.forgot_pwd);

        loadingLinear = (LinearLayout) findViewById(R.id.loading_linear);

        signinBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        forgotPwdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signin:

                inputValidationState = validateUserSigninInput();

                requestServerData = new HashMap<>();
                requestServerData.put("emailOrUsername",usernameOrEmailStr);
                requestServerData.put("pass",passStr);

                url = "/login.php";

                if(inputValidationState == null){

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            signinBtn.setVisibility(View.GONE);
                            loadingLinear.setVisibility(View.VISIBLE);

                            new GetDataFromServer(requestServerData, url, new UrlCallBack() {
                                @Override
                                public void done(String response) {
                                    if(response == null){

                                    }else{
                                        handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                                        dataFromServerArraylist = handleJsonDataFromServer.userContentFromServer();

                                        if(dataFromServerArraylist == null){
                                            errorTv.setText("Sorry, you are not able to login try again!");
                                            signinBtn.setVisibility(View.VISIBLE);
                                            loadingLinear.setVisibility(View.GONE);
                                        }else {
                                            user = localUserStorage.getSignedinUser();

                                            if (user.userId == -1) {
                                                user = new User(dataFromServerArraylist);
                                                localUserStorage.StoreUserData(user);
                                            }

                                            localUserStorage.setUserSignedinStatus(true);
                                            loginStatus = localUserStorage.getUserSignedinStatus();

                                            if (loginStatus == true) {
                                                intent = new Intent(ActivityMain.this, ActivityHome.class);
                                                startActivity(intent);
                                                signinBtn.setVisibility(View.VISIBLE);
                                                loadingLinear.setVisibility(View.GONE);
                                            } else {
                                                errorTv.setText("Sorry, you are not able to login, try again!");
                                                signinBtn.setVisibility(View.VISIBLE);
                                                loadingLinear.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                            }).execute();
                        }
                    },1000);

                }else{
                    errorTv.setText(inputValidationState);
                }
                break;
            case R.id.signup:
                intent = new Intent(ActivityMain.this,ActivitySignUp.class);
                startActivity(intent);
                break;
            case R.id.forgot_pwd:
                intent = new Intent(ActivityMain.this,ActivityForgotPassword.class);
                startActivity(intent);
                break;
        }
    }

    private String validateUserSigninInput(){
        String errorState = null;

        if(usernameOrEmailEt.getText().length() > 0){
            usernameOrEmailStr = usernameOrEmailEt.getText().toString();
        }else{
            errorState = "All fields must be filled!";
        }

        if(passEt.getText().length() > 0){
            passStr = passEt.getText().toString();
        }else{
            errorState = "All fields must be filled!";
        }

        return errorState;
    }
}
