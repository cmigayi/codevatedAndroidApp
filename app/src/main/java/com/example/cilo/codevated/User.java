package com.example.cilo.codevated;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/26/17.
 */

public class User {
    int userId;
    String username,email,profImg,gender,password,location,bio;

    public User(){}

    public User(ArrayList<HashMap<String,String>> arrayList){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(0);

        this.setUserId(Integer.parseInt(hashMap.get("user_id")));
        this.setUsername(hashMap.get("username"));
        this.setEmail(hashMap.get("email"));
        this.setPassword(hashMap.get("pass"));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfImg() {
        return profImg;
    }

    public void setProfImg(String profImg) {
        this.profImg = profImg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
