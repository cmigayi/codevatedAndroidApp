package com.example.cilo.codevated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/23/17.
 */

public class FragmentMyConcepts extends Fragment implements View.OnClickListener{
    ListView listView;
    LinearLayout noDataLinear;
    Button postBtn;
    Common common;

    Intent intent;

    LocalUserStorage localUserStorage;
    User user;
    String url;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    HashMap<String,String> dataSelectedHashMap, requestDataFromServer,dataFromServerHashmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_concepts,null);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());
        user=localUserStorage.getSignedinUser();

        listView = (ListView) view.findViewById(R.id.listview);
        noDataLinear = (LinearLayout) view.findViewById(R.id.no_data_linear_layout);
        postBtn = (Button) view.findViewById(R.id.post_btn);

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
                        dataFromServerHashmap = dataFromServerArraylist.get(0);
                        int state = Integer.parseInt(dataFromServerHashmap.get("state"));

                        if(state > 0){

                            noDataLinear.setVisibility(View.GONE);

                            CustomListviewUserConcepts customListviewUserConcepts =
                                    new CustomListviewUserConcepts(getActivity(), dataFromServerArraylist);
                            listView.setAdapter(customListviewUserConcepts);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap = dataFromServerArraylist.get(position);

                                    Intent intent = new Intent(getActivity(), ActivitySelectedConcept.class);
                                    intent.putExtra("conceptHashmap", hashMap);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }
        }).execute();

        postBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_btn:
                intent = new Intent(getActivity(),ActivityPostConcept.class);
                startActivity(intent);
                break;
        }
    }
}
