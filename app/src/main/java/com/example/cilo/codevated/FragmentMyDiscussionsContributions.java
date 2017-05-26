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
 * Created by cilo on 4/23/17.
 */

public class FragmentMyDiscussionsContributions extends Fragment {
    ListView listView;

    LocalUserStorage localUserStorage;
    User user;
    String url;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    HashMap<String,String> dataSelectedHashMap,requestDataFromServer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_discussions_contributions,null);

        listView = (ListView) view.findViewById(R.id.listview);

        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        url="/getUserDiscussionContributions.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);

        new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.getUserDiscussionsContributions();

                    if(dataFromServerArraylist == null){

                    }else{

                        CustomListviewMyDiscussionsContributions customListviewMyDiscussionsContributions
                                = new CustomListviewMyDiscussionsContributions(getActivity(),dataFromServerArraylist);

                        listView.setAdapter(customListviewMyDiscussionsContributions);
                    }
                }
            }
        }).execute();


        return view;
    }
}
