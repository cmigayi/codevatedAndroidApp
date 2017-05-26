package com.example.cilo.codevated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by cilo on 4/20/17.
 */

public class ActivitySplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    int progressStatus = 0;
    TextView textView2, textInfo;
    Handler handler = new Handler();
    LocalUserStorage localUserStorage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        localUserStorage = new LocalUserStorage(this);

        progressBar=(ProgressBar)findViewById(R.id.progressBar1);

        textView2 = (TextView) findViewById(R.id.load_per);
        textInfo = (TextView) findViewById(R.id.about_info);


        if(localUserStorage.getUserSignedinStatus() == true){
            intent = new Intent(this,ActivityHome.class);
            startActivity(intent);
        }else {
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 96) {
                        progressStatus += 8;
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                textView2.setText(progressStatus + "%");

                                if (progressStatus == 32) {
                                    textInfo.setText("Meet someone that will hold your hand through your IT professional journey");
                                    textInfo.setTextColor(Color.parseColor("#4aacf7"));
                                    progressBar.setBackgroundColor(Color.parseColor("#4aacf7"));
                                }

                                if (progressStatus == 64) {
                                    textInfo.setText("A platform that connects you to IT professionals & experts that will mentor you in various IT concepts");
                                    textInfo.setTextColor(Color.parseColor("#f7924a"));
                                    progressBar.setBackgroundColor(Color.parseColor("#f7924a"));
                                }
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (progressStatus == 96) {
                        Intent i = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                        startActivity(i);
                    }
                }
            }).start();
        }

    }
}
