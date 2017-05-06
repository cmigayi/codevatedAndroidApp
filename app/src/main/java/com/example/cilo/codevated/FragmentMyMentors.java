package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/23/17.
 */

public class FragmentMyMentors extends Fragment {
    GridView gridView;
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
        View view = inflater.inflate(R.layout.fragment_my_mentors,null);

        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        gridView = (GridView) view.findViewById(R.id.gridView);
        url = "/getAllUserMentors.php";
        requestFromServerHashmap = new HashMap<>();
        requestFromServerHashmap.put("user_id",""+user.userId);

        new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.getMentors();

                    if(dataFromServerArraylist == null){

                    }else{
                        Log.d("cilo90",""+dataFromServerArraylist);

                        CustomGridviewAllMentors customGridviewAllMentors =
                                new CustomGridviewAllMentors(getActivity(),dataFromServerArraylist);
                        gridView.setAdapter(customGridviewAllMentors);
                    }

                }

            }
        }).execute();

        return view;
    }
}
