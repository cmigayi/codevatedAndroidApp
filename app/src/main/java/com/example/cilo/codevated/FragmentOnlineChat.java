package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by cilo on 4/24/17.
 */

public class FragmentOnlineChat extends Fragment{
    String [] items ={"rerer","erewr","erewr"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_chat,null);

        ListView listView = (ListView) view.findViewById(R.id.listview);
        CustomListviewOnlineChat customListviewOnlineChat =
                new CustomListviewOnlineChat(getActivity(),items);
        listView.setAdapter(customListviewOnlineChat);

        return view;
    }
}
