package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/15/17.
 */

public class ActivityChatConversation extends AppCompatActivity implements View.OnClickListener{
    ImageView profImageV,imgRelation;
    TextView usernameTv;
    Button postBtn;
    EditText chatMsg;
    ListView listView;
    String chatMsgStr;
    Intent intent;
    HashMap<String,String> hashmapFromLastpage;

    Common common;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestDataFromServer;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        overridePendingTransition(0,0);

        common = new Common(this);
        localUserStorage = new LocalUserStorage(this);
        user = localUserStorage.getSignedinUser();

        intent = getIntent();
        hashmapFromLastpage = (HashMap<String, String>) intent.getSerializableExtra("chatmap");

        profImageV = (ImageView) findViewById(R.id.prof_img);
        imgRelation = (ImageView) findViewById(R.id.img_relation);
        usernameTv = (TextView) findViewById(R.id.username);
        postBtn = (Button) findViewById(R.id.post_btn);
        chatMsg = (EditText) findViewById(R.id.message);
        listView = (ListView) findViewById(R.id.listview);

        usernameTv.setText(hashmapFromLastpage.get("username"));
        if(hashmapFromLastpage.get("prof_img").equals("") || hashmapFromLastpage.get("prof_img").equals("n")){

        }else{
            Picasso.with(this).load(hashmapFromLastpage.get("prof_img")).transform(new CircleTransform()).into(profImageV);
        }

        if(hashmapFromLastpage.get("relation").equals("mentor")){
            imgRelation.setImageResource(R.drawable.mentor);
        }

        url="/getChatMessages.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);
        requestDataFromServer.put("respondent",hashmapFromLastpage.get("user_id"));

        new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.chatConversations();

                    if(dataFromServerArraylist == null){

                    }else{
                        CustomListviewConversationChat customListviewConversationChat =
                                new CustomListviewConversationChat(getBaseContext(),dataFromServerArraylist,
                                        hashmapFromLastpage);

                        listView.setAdapter(customListviewConversationChat);
                    }
                }
            }
        }).execute();

        postBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_btn:
                chatMsgStr = chatMsg.getText().toString();

                url="/postChatMessage.php";
                requestDataFromServer = new HashMap<>();
                requestDataFromServer.put("user_id",""+user.userId);
                requestDataFromServer.put("respondent",hashmapFromLastpage.get("user_id"));
                requestDataFromServer.put("message",""+chatMsgStr);

                new SendDataToServer(requestDataFromServer, url, new UrlCallBack() {
                    @Override
                    public void done(String response) {
                        if(response == null){

                        }else{
                            handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                            dataFromServerArraylist = handleJsonDataFromServer.chatConversations();

                            if(dataFromServerArraylist == null){

                            }else{
                                CustomListviewConversationChat customListviewConversationChat =
                                        new CustomListviewConversationChat(getBaseContext(),dataFromServerArraylist,
                                                hashmapFromLastpage);

                                listView.setAdapter(customListviewConversationChat);
                            }
                        }
                    }
                }).execute();
                chatMsg.setText("");
                break;
        }
    }
}
