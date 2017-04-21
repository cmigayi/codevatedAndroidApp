package com.example.cilo.codevated;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by cilo on 4/20/17.
 */

public class FragmentConcepts extends Fragment implements View.OnClickListener{
    String[] items = {"dsd","sdsa","dsada","sdsadsa","dsadsa","sdsd"};
    ListView listView;
    Button searchBtn,typeBtn;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concepts,null);

        listView = (ListView) view.findViewById(R.id.listview);
        searchBtn = (Button) view.findViewById(R.id.search_btn);
        typeBtn = (Button) view.findViewById(R.id.type_btn);

        CustomListviewAllConcepts customListviewAllConcepts = new CustomListviewAllConcepts(getActivity(),items);
        listView.setAdapter(customListviewAllConcepts);

        searchBtn.setOnClickListener(this);
        typeBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search_btn:
                searchPopUp(getActivity());
                break;
            case R.id.type_btn:
                findTypePopUp(getActivity());
                break;
        }
    }

    public void searchPopUp(Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_concept_search);
        dialog.closeOptionsMenu();
        dialog.setTitle("");
        dialog.show();
    }

    public void findTypePopUp(Context context){
        String[] types = {"Android","Java","CSS"};

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_find_type);
        dialog.closeOptionsMenu();
        dialog.setTitle("Specific type/s:");

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        CustomListviewInterestTypeCheckbox customListviewInterestTypeCheckbox =
                new CustomListviewInterestTypeCheckbox(context,types);
        listView.setAdapter(customListviewInterestTypeCheckbox);

        dialog.show();
    }
}
