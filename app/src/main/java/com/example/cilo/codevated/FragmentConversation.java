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

public class FragmentConversation extends Fragment {
    ListView listView;
    String [] items = {"dfdfsd","fdsf","dfdsfds"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation_chat,null);
        listView = (ListView) view.findViewById(R.id.listview);

        CustomListviewConversationChat customListviewConversationChat =
                new CustomListviewConversationChat(getActivity(),items);
        listView.setAdapter(customListviewConversationChat);

        return view;
    }
}
