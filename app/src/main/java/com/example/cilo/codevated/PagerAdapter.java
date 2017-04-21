package com.example.cilo.codevated;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by cilo on 4/20/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    FragmentManager fm;
    String activityName;

    public PagerAdapter(FragmentManager fm,int numOfTabs, String activityName) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.activityName = activityName;
    }

    @Override
    public Fragment getItem(int position) {
        if(activityName == "Home"){
            switch (position){
                case 0:
                    FragmentLastedPost fragmentLastedPost = new FragmentLastedPost();
                    return fragmentLastedPost;
                case 1:
                    FragmentConcepts fragmentConcepts = new FragmentConcepts();
                    return new FragmentConcepts();
                case 2:
                    FragmentCircles fragmentCircles = new FragmentCircles();
                    return fragmentCircles;

            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
