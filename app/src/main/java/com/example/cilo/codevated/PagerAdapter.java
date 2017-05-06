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
    FragmentDiscussions fragmentDiscussions;

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
        if(activityName == "UserProfile"){
            switch (position){
                case 0:
                    FragmentMyConcepts fragmentMyConcepts = new FragmentMyConcepts();
                    return fragmentMyConcepts;
                case 1:
                    FragmentMyCircles fragmentMyCircles = new FragmentMyCircles();
                    return fragmentMyCircles;
                case 2:
                    FragmentMyMentors fragmentMyMentors = new FragmentMyMentors();
                    return fragmentMyMentors;
                case 3:
                    FragmentsMyMentees fragmentsMyMentees = new FragmentsMyMentees();
                    return fragmentsMyMentees;
                case 4:
                    FragmentMyDiscussions fragmentMyDiscussions = new FragmentMyDiscussions();
                    return fragmentMyDiscussions;
                case 5:
                    FragmentMyChats fragmentMyChats = new FragmentMyChats();
                    return fragmentMyChats;
            }
        }
        if(activityName == "Discussions"){
            switch (position){
                case 0:
                    fragmentDiscussions = new FragmentDiscussions();
                    return fragmentDiscussions;
                case 1:
                    fragmentDiscussions = new FragmentDiscussions();
                    return fragmentDiscussions;
            }
        }
        if(activityName == "Chats"){
            switch (position){
                case 0:
                    FragmentOnlineChat fragmentOnlineChat = new FragmentOnlineChat();
                    return fragmentOnlineChat;
                case 1:
                    FragmentConversation fragmentConversation = new FragmentConversation();
                    return fragmentConversation;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
