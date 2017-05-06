package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/23/17.
 */

public class FragmentMyConcepts extends Fragment implements View.OnClickListener{
    ListView listView;
    Common common;

    LocalUserStorage localUserStorage;
    User user;
    String url;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    HashMap<String,String> dataSelectedHashMap;
    HashMap<String,String> requestDataFromServer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_concepts,null);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());
        user=localUserStorage.getSignedinUser();

        listView = (ListView) view.findViewById(R.id.listview);

        url="/getUserConcepts.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);

        new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.getPosts();

                    if(dataFromServerArraylist == null){

                    }else{
                        CustomListviewUserConcepts customListviewUserConceptss =
                                new CustomListviewUserConcepts(getActivity(),dataFromServerArraylist);
                        listView.setAdapter(customListviewUserConceptss);
                    }
                }
            }
        }).execute();

        return view;
    }

    @Override
    public void onClick(View v) {
    }
}
