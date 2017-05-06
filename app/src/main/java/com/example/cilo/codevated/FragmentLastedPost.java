package com.example.cilo.codevated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/20/17.
 */

public class FragmentLastedPost extends Fragment implements View.OnClickListener {
    ListView listView;
    TextView latestPostStatusTv;
    Button viewBtn;
    LinearLayout loadingLinear,linearNoInternet;
    RelativeLayout relativePostedItems;
    LocalUserStorage localUserStorage;
    User user;
    String url;
    HashMap<String,String> requestDataFromServer;
    HandleJsonDataFromServer handleJsonDataFromServer;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    int count = 0;

    BroadcastReceiver broadcastReceiver;
    FragmentLastedPost fragmentLastedPost;

    Common common;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_post,container,false);

        common = new Common(getActivity());
        localUserStorage = new LocalUserStorage(getActivity());

        listView = (ListView) view.findViewById(R.id.listview);
        latestPostStatusTv = (TextView) view.findViewById(R.id.latest_post_status);
        viewBtn = (Button) view.findViewById(R.id.view);
        loadingLinear = (LinearLayout) view.findViewById(R.id.loading_linear);
        linearNoInternet = (LinearLayout) view.findViewById(R.id.linear_no_internet);
        relativePostedItems = (RelativeLayout) view.findViewById(R.id.relative_posted_items);

        user = localUserStorage.getSignedinUser();
        url = "/latestPosts.php";
        requestDataFromServer = new HashMap<>();
        requestDataFromServer.put("user_id",""+user.userId);
        requestDataFromServer.put("num_rows","");
        Log.d("cilo",""+user.userId);

        if(common.isNetworkAvailable() == false){

            new GetDataFromServer(requestDataFromServer, url, new UrlCallBack() {
                @Override
                public void done(String response) {

                    if(response == null){

                    }else{

                        handleJsonDataFromServer = new HandleJsonDataFromServer(response);
                        dataFromServerArraylist = handleJsonDataFromServer.getPosts();

                        if(dataFromServerArraylist == null){
                            latestPostStatusTv.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            viewBtn.setVisibility(View.GONE);
                            latestPostStatusTv.setText("You should add interests to get posts!");
                        }else {
                            count = dataFromServerArraylist.size();
                            CustomListviewLatestPost customListviewLatestPost =
                                    new CustomListviewLatestPost(getActivity(), dataFromServerArraylist);
                            listView.setAdapter(customListviewLatestPost);
                        }
                    }

                }
            }).execute();

            relativePostedItems.setVisibility(View.VISIBLE);
            linearNoInternet.setVisibility(View.GONE);

            getActivity().startService(new Intent(getActivity(),ServiceLatestPost.class));

        }else{
            relativePostedItems.setVisibility(View.GONE);
            linearNoInternet.setVisibility(View.VISIBLE);
            getActivity().stopService(new Intent(getActivity(),ServiceLatestPost.class));
        }


        viewBtn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (common.isNetworkAvailable() == false) {

        }else{
            if (broadcastReceiver == null) {
                broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String item = intent.getStringExtra("data");
                        Log.d("cilo11111", item);
                        handleJsonDataFromServer = new HandleJsonDataFromServer(item);
                        dataFromServerArraylist = handleJsonDataFromServer.getPosts();
                        if (dataFromServerArraylist == null) {

                        } else {

                            if (dataFromServerArraylist.size() > count) {
                                int count2 = dataFromServerArraylist.size() - count;
                                viewBtn.setText("Click to view " + count2 + " new posts");
                                viewBtn.setVisibility(View.VISIBLE);
                            } else {
                                viewBtn.setVisibility(View.GONE);
                            }
                        }
                    }
                };
            }
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter("latestPosts"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.view:
                fragmentLastedPost = new FragmentLastedPost();
                getActivity().getSupportFragmentManager().
                        beginTransaction().add(fragmentLastedPost.getId(),fragmentLastedPost).commit();
                break;
        }
    }
}
