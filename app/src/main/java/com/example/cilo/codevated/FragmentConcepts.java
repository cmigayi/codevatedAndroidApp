package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/20/17.
 */

public class FragmentConcepts extends Fragment implements View.OnClickListener{
    GridView gridView;
    Common common;
    LocalUserStorage localUserStorage;
    User user;
    String url;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    HashMap<String,String> dataSelectedHashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concepts,null);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());
        user = localUserStorage.getSignedinUser();

        gridView = (GridView) view.findViewById(R.id.gridView);

        url = "/getInterests.php";
        HashMap<String,String> requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);

        new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{

                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist= handleJsonDataFromServer.signupPageInterestGridViewData();

                    CustomGridviewAllConcepts customGridviewAllConcepts =
                            new CustomGridviewAllConcepts(getActivity(),dataFromServerArraylist);
                    gridView.setAdapter(customGridviewAllConcepts);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dataSelectedHashMap = dataFromServerArraylist.get(position);

                            Log.d("vil",dataSelectedHashMap.get("interest_id"));
                            Intent intent = new Intent(getActivity(),ActivityInterestConcepts.class);
                            intent.putExtra("dataSelectedHashmap",dataSelectedHashMap);
                            getActivity().startActivity(intent);
                        }
                    });
                }
            }
        }).execute();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
        }
    }
}
