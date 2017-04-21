package com.example.cilo.codevated;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by cilo on 4/20/17.
 */

public class FragmentCircles extends Fragment {
    Circle circle;
    GridView gridView;
    String [] circles = {"dfsf","dfdf sdasfsd dsdas sfsfdf dsfdf fdsfdsf fdsfsdf","dfdsf","Android january class 2017"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circles,container,false);
        gridView = (GridView) view.findViewById(R.id.gridView);

        CustomGridviewAllCircles customGridviewAllCircles = new CustomGridviewAllCircles(getActivity(),circles);

        gridView.setAdapter(customGridviewAllCircles);
        return view;
    }
}
