package com.example.cilo.codevated;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by cilo on 4/22/17.
 */

public class Common {
    Dialog dialog;
    private Context context;
    Intent intent;
    LocalUserStorage localUserStorage;
    User user;

    public Common(Context context){
        this.context = context;
    }

    public void moreMenuPopUp(){
        String[] items = {"Mentors","Mentees","Discussions","Chats","Profile","Account","Blocked users","Sign out"};
        int[] icons = {R.drawable.mentor,R.drawable.mentee,R.drawable.discussion,R.drawable.chat,
                R.drawable.female_user_dark,R.drawable.accessibility,R.drawable.remove,R.drawable.exit};

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_more_menu_items);
        dialog.closeOptionsMenu();
        dialog.setTitle("...more menu items");

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        CustomListviewMoreMenuItems customListviewMoreMenuItems =
                new CustomListviewMoreMenuItems(context, items,icons);
        listView.setAdapter(customListviewMoreMenuItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        intent = new Intent(context,ActivityMentors.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context,ActivityMentees.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context,ActivityDiscussions.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context,ActivityChats.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(context,ActivityProfileEdit.class);
                        context.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(context,ActivityProfileAccount.class);
                        context.startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(context,ActivityProfileBlockUsers.class);
                        context.startActivity(intent);
                        break;
                    case 7:
                        localUserStorage = new LocalUserStorage(context);

                        if(localUserStorage.getUserSignedinStatus() == true){
                            localUserStorage.clearuserData();
                            intent = new Intent(context,ActivityMain.class);
                            context.startActivity(intent);
                        }else{
                            intent = new Intent(context,ActivityMain.class);
                            context.startActivity(intent);
                        }
                        break;
                }
            }
        });

        dialog.show();
    }

    public void userProfilePopUp(){
        String[] items = {"Profile","Account","Blocked users","Sign out"};
        int[] icons = {R.drawable.female_user_dark,R.drawable.accessibility,R.drawable.remove,R.drawable.exit};

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_more_menu_items);
        dialog.closeOptionsMenu();
        dialog.setTitle("...more profile items");

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        CustomListviewMoreMenuItems customListviewMoreMenuItems =
                new CustomListviewMoreMenuItems(context, items,icons);
        listView.setAdapter(customListviewMoreMenuItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        intent = new Intent(context,ActivityProfileEdit.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context,ActivityProfileAccount.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context,ActivityProfileBlockUsers.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        localUserStorage.clearuserData();
                        intent = new Intent(context,ActivityMain.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });

        dialog.show();
    }

    public void inputMediaData(){
        String[] items = {"add a Web link","add a Youtube link","add an Image","add a Video"};

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_input_media_data_items);
        dialog.closeOptionsMenu();
        dialog.setTitle("...add media data");

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,items);

        listView.setAdapter(adapter);

        dialog.show();
    }

    public void searchPopUp(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_concept_search);
        dialog.closeOptionsMenu();
        dialog.setTitle("");
        dialog.show();
    }

    public void findTypePopUp(){
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

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 160;

            // Find the correct scale value. It should be the power of
            // 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE
                    && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isNetworkAvailable(){
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
    }
}
