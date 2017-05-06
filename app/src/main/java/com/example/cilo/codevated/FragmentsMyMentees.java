package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by cilo on 4/23/17.
 */

public class FragmentsMyMentees extends Fragment {
    GridView gridView;
    String [] mentees = {"Jklh","dssd","dsads","dfdfdsfdf"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_mentees,null);

        gridView = (GridView) view.findViewById(R.id.gridView);

//        CustomGridviewAllMentors customGridviewAllMentors = new CustomGridviewAllMentors(getActivity(),mentees);
//        gridView.setAdapter(customGridviewAllMentors);

        return view;
    }
}
