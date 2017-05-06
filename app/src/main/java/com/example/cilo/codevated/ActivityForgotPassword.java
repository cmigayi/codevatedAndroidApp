package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by cilo on 4/24/17.
 */

public class ActivityForgotPassword extends AppCompatActivity implements View.OnClickListener{
    Button backBtn,submitBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        overridePendingTransition(0, 0);

        backBtn = (Button) findViewById(R.id.back);
        submitBtn = (Button) findViewById(R.id.submit);

        backBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submit:
                break;
            case R.id.back:
                intent = new Intent(getApplicationContext(),ActivityMain.class);
                startActivity(intent);
                break;
        }
    }
}
