package com.example.cilo.codevated;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cilo on 4/28/17.
 */

public class LocalUserStorage {
    SharedPreferences userStorage;
    static String USER_DATA = "user";
    SharedPreferences.Editor editor;

    public LocalUserStorage(Context context){
        userStorage = context.getSharedPreferences(USER_DATA,0);
    }

    public void StoreUserData(User user){
        editor = userStorage.edit();

        editor.putInt("user_id",user.userId);
        editor.putString("username",user.username);
        editor.putString("email",user.email);
        editor.putString("password",user.password);
        editor.putString("gender",user.gender);

        editor.commit();
    }

    public User getSignedinUser(){
        int user_id = userStorage.getInt("user_id",-1);
        String username = userStorage.getString("username","");
        String email = userStorage.getString("email","");
        String password = userStorage.getString("password","");
        String gender = userStorage.getString("gender","");

        User user = new User();
        user.setUserId(user_id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);

        return user;
    }

    public void setUserSignedinStatus(boolean status){
        editor = userStorage.edit();
        editor.putBoolean("signedIn",status);
        editor.commit();
    }

    public boolean getUserSignedinStatus(){
        boolean status = userStorage.getBoolean("signedIn",false);
        return status;
    }

    public void clearuserData(){
        editor = userStorage.edit();
        editor.clear();
        editor.commit();
    }
}

