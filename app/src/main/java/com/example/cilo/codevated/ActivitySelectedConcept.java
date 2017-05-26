package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by cilo on 5/18/17.
 */

public class ActivitySelectedConcept extends AppCompatActivity {
    Intent intent;
    HashMap<String,String> hashMap;

    TextView title,content;
    ImageView profImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_concept);
        overridePendingTransition(0,0);

        intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("conceptHashmap");

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        profImg = (ImageView) findViewById(R.id.prof_img);

        title.setText(hashMap.get("name"));
        content.setText(hashMap.get("concept_content"));
        Picasso.with(this).load(hashMap.get("profile_img")).transform(new CircleTransform()).into(profImg);
    }
}
