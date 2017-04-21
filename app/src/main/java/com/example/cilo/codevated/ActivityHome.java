package com.example.cilo.codevated;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by cilo on 4/20/17.
 */

public class ActivityHome extends AppCompatActivity {
    Toolbar toolbar;
    ImageView menuImg;
    TabLayout tabLayout;
    ViewPager viewPager;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuImg = (ImageView) toolbar.findViewById(R.id.menu_icon);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreMenuPopUp(ActivityHome.this);
            }
        });

        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Latest Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Concepts"));
        tabLayout.addTab(tabLayout.newTab().setText("Circles"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),"Home");

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void moreMenuPopUp(Context context){
        String[] items = {"Mentor","Mentee","Discussion","Chat"};
        int[] icons = {R.drawable.mentor,R.drawable.mentee,R.drawable.discussion,R.drawable.chat};

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_more_menu_items);
        dialog.closeOptionsMenu();
        dialog.setTitle("...more menu items");

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        CustomListviewMoreMenuItems customListviewMoreMenuItems =
                new CustomListviewMoreMenuItems(context, items,icons);
        listView.setAdapter(customListviewMoreMenuItems);

        dialog.show();
    }
}
