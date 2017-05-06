package com.example.cilo.codevated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/26/17.
 */

public class ActivityProfileEdit extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg,profImg;
    EditText emailEt,bioEt,locationEt;
    TextView notifyCount;
    Button updateProfBtn,changePicBtn,uploadPicBtn;
    Intent intent;
    Common common;
    Bitmap pic;

    public static final int REQUEST_CAMERA = 1;
    public static final int SELECT_FILE = 2;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
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

        emailEt = (EditText) findViewById(R.id.email);
        bioEt = (EditText) findViewById(R.id.bio);
        locationEt = (EditText) findViewById(R.id.location);
        profImg = (ImageView) findViewById(R.id.prof_img);

        emailEt.setText(user.email);
        bioEt.setText(user.bio);
        locationEt.setText(user.location);

        changePicBtn = (Button) findViewById(R.id.change_pic_btn);
        uploadPicBtn = (Button) findViewById(R.id.upload_pic_btn);
        updateProfBtn = (Button) findViewById(R.id.update_prof_btn);

        changePicBtn.setOnClickListener(this);
        updateProfBtn.setOnClickListener(this);
        uploadPicBtn.setOnClickListener(this);
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
            case R.id.change_pic_btn:
                selectImage();
                break;
            case R.id.upload_pic_btn:
                Bitmap map = getCurrentImg();
                if(map != null) {
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    map.compress(Bitmap.CompressFormat.PNG, 0, bs);
                    //intent.putExtra("bitmap", bs.toByteArray());
                    //startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_OK){
            switch(resultCode){
                case REQUEST_CAMERA:
                        Bitmap thumbnail = (Bitmap) intent.getExtras().get("data");
                        profImg.setImageBitmap(thumbnail);
                        setCurrentImg(thumbnail);
                    break;
                case SELECT_FILE:
                        String selectedImageUri = common.getAbsolutePath(intent.getData());
                        profImg.setImageBitmap(common.decodeFile(selectedImageUri));
                        setCurrentImg(common.decodeFile(selectedImageUri));
                    break;
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SELECT_FILE);
                }else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void setCurrentImg(Bitmap pic){
        this.pic = pic;
    }

    private  Bitmap getCurrentImg(){
        return this.pic;
    }
}
