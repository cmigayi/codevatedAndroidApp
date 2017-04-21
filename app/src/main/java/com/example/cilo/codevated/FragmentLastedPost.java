package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by cilo on 4/20/17.
 */

public class FragmentLastedPost extends Fragment {
    String[] items = {"dsd","sdsa","dsada","sdsadsa","dsadsa","sdsd"};
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_post,container,false);

        listView = (ListView) view.findViewById(R.id.listview);

        CustomListviewLatestPost customListviewLatestPost = new CustomListviewLatestPost(getActivity(),items);

        listView.setAdapter(customListviewLatestPost);

        return view;
    }
}
