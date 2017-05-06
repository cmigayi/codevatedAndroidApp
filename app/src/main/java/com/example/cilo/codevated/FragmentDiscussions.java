package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by cilo on 4/24/17.
 */

public class FragmentDiscussions extends Fragment {
    ListView listView;
    String [] discussions = {"The best business models to adopt when running an IT consulting business with industrial examples",
            "Application of AI in Kenya or Africa"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions,container,false);
        listView = (ListView) view.findViewById(R.id.listview);

        CustomListviewAllDiscussions customListviewAllDiscussions =
                new CustomListviewAllDiscussions(getActivity(),discussions);

        listView.setAdapter(customListviewAllDiscussions);

        return view;
    }
}
