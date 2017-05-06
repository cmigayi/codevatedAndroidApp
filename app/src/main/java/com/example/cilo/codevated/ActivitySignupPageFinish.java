package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by cilo on 4/28/17.
 */

public class ActivitySignupPageFinish extends AppCompatActivity implements View.OnClickListener {
    Button finishBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page_finish);

        finishBtn = (Button) findViewById(R.id.finish_btn);

        finishBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.finish_btn:
                intent = new Intent(getApplicationContext(),ActivityHome.class);
                startActivity(intent);
                break;
        }
    }
}
