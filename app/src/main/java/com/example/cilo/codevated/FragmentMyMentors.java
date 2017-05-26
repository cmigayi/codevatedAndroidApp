package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/23/17.
 */

public class FragmentMyMentors extends Fragment implements View.OnClickListener{
    GridView gridView;
    LinearLayout noDataLinear;
    Button addMentorBtn;
    Common common;

    Intent intent;

    LocalUserStorage localUserStorage;
    User user;

    HashMap<String,String> requestFromServerHashmap,dataFromServerhashmap;
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
        noDataLinear = (LinearLayout) view.findViewById(R.id.no_data_linear_layout);
        addMentorBtn = (Button) view.findViewById(R.id.add_mentor);

        url = "/getAllUserMentors.php";
        requestFromServerHashmap = new HashMap<>();
        requestFromServerHashmap.put("user_id",""+user.userId);

        new GetDataFromServer(requestFromServerHashmap, url, new UrlCallBack() {
            @Override
            public void done(String response) {
                if(response == null){

                }else{
                    handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                    dataFromServerArraylist = handleJsonDataFromServer.getMentorsOrMentees();

                    if(dataFromServerArraylist == null){

                    }else{
                        dataFromServerhashmap = dataFromServerArraylist.get(0);
                        int state = Integer.parseInt(dataFromServerhashmap.get("state"));

                        Log.d("cilo123",""+state);

                        if(state == 0){
                            noDataLinear.setVisibility(View.VISIBLE);
                        }else {
                            Log.d("cilo90", "" + dataFromServerArraylist);

                            CustomGridviewAllMentors customGridviewAllMentors =
                                    new CustomGridviewAllMentors(getActivity(), dataFromServerArraylist);
                            gridView.setAdapter(customGridviewAllMentors);
                        }
                    }

                }

            }
        }).execute();

        addMentorBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_mentor:
                intent = new Intent(getActivity(),ActivityMentors.class);
                startActivity(intent);
                break;
        }
    }
}
