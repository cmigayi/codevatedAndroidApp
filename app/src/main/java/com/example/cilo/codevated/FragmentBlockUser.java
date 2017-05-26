package com.example.cilo.codevated;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/11/17.
 */

public class FragmentBlockUser extends Fragment implements View.OnClickListener{
    ImageView menuImg, postConceptImg,logoImg,notifyImg,userImg,profImg;
    TextView firstInfo, notifyCount,errorsTv;
    ListView listView;
    EditText usernameEt;
    Button blockUserBtn;
    Intent intent;
    Common common;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap,dataFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    BroadcastReceiver broadcastReceiver;
    String usernameStr = null;
    String blockedUserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block_user,null);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        firstInfo = (TextView) view.findViewById(R.id.first_info);
        firstInfo.append(
                "\n" +
                        "\n1. Accessing and commenting on your posts.\n" +
                        "\n2. Inviting you to their circle.\n" +
                        "\n3. Viewing your activities.\n" +
                        "\n4. Sending you any notifications.\n" +
                        "\n5. Viewing your profile.\n");

        usernameEt = (EditText) view.findViewById(R.id.usernameEt);
        errorsTv = (TextView) view.findViewById(R.id.errors);
        profImg = (ImageView) view.findViewById(R.id.prof_img);
        blockUserBtn = (Button) view.findViewById(R.id.block_user_btn);

        usernameEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(usernameEt.getText().toString().length() > 0){
                    usernameStr = usernameEt.getText().toString();
                }else{

                }
                url = "/searchUsername.php";
                requestFromServerHashmap = new HashMap<String, String>();
                requestFromServerHashmap.put("user_id",""+user.userId);
                requestFromServerHashmap.put("username",usernameStr);

                new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            dataFromServerArraylist = handleJsonDataFromServer.searchUsername();


                            if(dataFromServerArraylist == null){
                                profImg.setImageResource(R.drawable.female_user_grey);
                                errorsTv.setText("Sorry "+usernameStr+" does not exist. Keep typing, maybe the name " +
                                        "is incomplete.");
                                errorsTv.setTextColor(Color.parseColor("#cc2118"));
                                blockUserBtn.setEnabled(false);
                            }else {
                                dataFromServerHashmap = dataFromServerArraylist.get(0);
                                Log.d("cilo104", "" + dataFromServerHashmap);

                                if(dataFromServerHashmap.get("prof_img").equals("n") ||
                                        dataFromServerHashmap.get("prof_img").equals("")){

                                    profImg.setImageResource(R.drawable.female_user_grey);
                                }else {
                                    Picasso.with(getActivity()).
                                            load(dataFromServerHashmap.get("prof_img")).
                                            transform(new CircleTransform()).into(profImg);
                                }
                                errorsTv.setText(usernameStr+" found. You are about to block "+usernameStr+"!");
                                errorsTv.setTextColor(Color.parseColor("#4b88cc"));
                                blockUserBtn.setEnabled(true);
                            }
                        }
                    }
                }).execute();

                return false;
            }
        });

        blockUserBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.block_user_btn:
                url="/blockUser.php";
                blockedUserID = dataFromServerHashmap.get("user_id");
                requestFromServerHashmap.put("user_id",""+user.userId);
                requestFromServerHashmap.put("blocked_user",blockedUserID);

                Log.d("cilo104",""+requestFromServerHashmap);

                new SendDataToServer(requestFromServerHashmap, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            dataFromServerArraylist = handleJsonDataFromServer.blockedUsers();

                            if(dataFromServerArraylist == null){

                            }else{
                                dataFromServerHashmap = dataFromServerArraylist.get(0);
                                Log.d("cilo104",""+dataFromServerHashmap);
                            }

                        }
                    }
                }).execute();

                break;
        }
    }
}
