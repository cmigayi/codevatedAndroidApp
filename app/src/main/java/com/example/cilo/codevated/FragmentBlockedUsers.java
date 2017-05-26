package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 5/11/17.
 */

public class FragmentBlockedUsers extends Fragment {
    ListView listView;
    TextView blockedUsersStatus;

    Intent intent;
    Common common;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int dataFromServerState;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocked_users,null);
        listView = (ListView) view.findViewById(R.id.listview);
        blockedUsersStatus = (TextView) view.findViewById(R.id.blocked_users_status);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        url="/getUserBlockedUsers.php";
        requestFromServerHashmap = new HashMap<>();
        requestFromServerHashmap.put("user_id",""+user.userId);

        new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.blockedUsers();

                    if(dataFromServerArraylist == null){
                        blockedUsersStatus.setVisibility(View.VISIBLE);
                    }else{
                        CustomListviewBlockedusers customListviewBlockedusers =
                                new CustomListviewBlockedusers(getActivity(),dataFromServerArraylist);

                        listView.setAdapter(customListviewBlockedusers);
                    }

                }
            }
        }).execute();


        return view;
    }
}
