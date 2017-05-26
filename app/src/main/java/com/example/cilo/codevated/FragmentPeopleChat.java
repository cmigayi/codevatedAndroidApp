package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/24/17.
 */

public class FragmentPeopleChat extends Fragment{
    ListView listView;
    LocalUserStorage localUserStorage;
    Common common;
    User user;

    String url;
    HashMap<String,String> requestDataFromServer;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_chat,null);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        listView = (ListView) view.findViewById(R.id.listview);

        url = "/getPeopleChat.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);

        new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
            @Override
            public void done(String response) {

                if(response == null){

                }else{

                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.peopleChat();

                    if(dataFromServerArraylist == null){

                    }else{

                        CustomListviewPeopleChat customListviewPeopleChat =
                                new CustomListviewPeopleChat(getActivity(),dataFromServerArraylist);
                        listView.setAdapter(customListviewPeopleChat);
                    }
                }

            }
        }).execute();

        return view;
    }
}
