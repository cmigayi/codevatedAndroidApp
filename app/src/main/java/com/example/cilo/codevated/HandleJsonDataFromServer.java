package com.example.cilo.codevated;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/26/17.
 */

public class HandleJsonDataFromServer {
    JSONObject jsonObject;
    JSONArray dataFromServerJsonArray;
    HashMap<String,String> dataFromServerHashMap;
    ArrayList<HashMap<String,String>> dataFromServerArraylist;
    String response;

    public HandleJsonDataFromServer(String response){
        this.response = response;
        dataFromServerArraylist = new ArrayList<HashMap<String, String>>();
    }

    public  ArrayList<HashMap<String,String>> userContentFromServer(){

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("userContent");

            JSONObject dataItem = dataFromServerJsonArray.getJSONObject(0);

            if(dataItem.getString("status").equals("success")) {

                String userId = dataItem.getString("user_id");
                String username = dataItem.getString("username");
                String email = dataItem.getString("email");
                String pass = dataItem.getString("pass");
                String industry = dataItem.getString("industry");
                String speciality = dataItem.getString("speciality");

                dataFromServerHashMap = new HashMap<String, String>();
                dataFromServerHashMap.put("user_id", userId);
                dataFromServerHashMap.put("username", username);
                dataFromServerHashMap.put("email", email);
                dataFromServerHashMap.put("pass", pass);
                dataFromServerHashMap.put("industry", industry);
                dataFromServerHashMap.put("speciality", speciality);

                dataFromServerArraylist.add(dataFromServerHashMap);
            }else{
                dataFromServerArraylist = null;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return dataFromServerArraylist;
    }

    public ArrayList<HashMap<String,String>> signupPageSpinnerIndustryData(){
        Log.d("hapa",response);

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("industries");

            for(int i=0; i<dataFromServerJsonArray.length(); i++){

                JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);

                String industryId = dataItem.getString("industry_id");
                String industryName = dataItem.getString("industry_name");

                dataFromServerHashMap = new HashMap<String, String>();
                dataFromServerHashMap.put("industry_id",industryId);
                dataFromServerHashMap.put("industry_name",industryName);

                dataFromServerArraylist.add(dataFromServerHashMap);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return dataFromServerArraylist;
    }

    public ArrayList<HashMap<String,String>> signupPageSpinnerSpecialityData(String id){

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("specialities");

            for(int i=0; i<dataFromServerJsonArray.length(); i++){

                JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);
                String specialityId = dataItem.getString("speciality_id");
                String specialityName = dataItem.getString("speciality_name");
                String industryId = dataItem.getString("industry_id");

                if(id.equals(industryId)){
                    dataFromServerHashMap = new HashMap<String, String>();
                    dataFromServerHashMap.put("speciality_id", specialityId);
                    dataFromServerHashMap.put("speciality_name", specialityName);
                }else{
                    dataFromServerArraylist = null;
                }

                dataFromServerArraylist.add(dataFromServerHashMap);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("hapa",""+dataFromServerArraylist);
        return dataFromServerArraylist;
    }

    public ArrayList<HashMap<String,String>> signupPageInterestGridViewData(){
        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("interests");

            for(int i=0;i<dataFromServerJsonArray.length();i++){
                JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);

                String interestID = dataItem.getString("interest_cartegory_id");
                String specialityID = dataItem.getString("speciality_id");
                String cartegoryName = dataItem.getString("cartegory_name");
                String cartegoryTotal = dataItem.getString("cartegory_total");
                String cartegoryIcon = dataItem.getString("cartegory_icon");

                dataFromServerHashMap = new HashMap<>();
                dataFromServerHashMap.put("interest_id",interestID);
                dataFromServerHashMap.put("speciality_id",specialityID);
                dataFromServerHashMap.put("cartegory_name",cartegoryName);
                dataFromServerHashMap.put("cartegory_total",cartegoryTotal);
                dataFromServerHashMap.put("cartegory_icon",cartegoryIcon);

                dataFromServerArraylist.add(dataFromServerHashMap);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return dataFromServerArraylist;
    }

    public int checkIfInputFieldExists(){
        int state = -1;

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                state = -1;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("data");

            JSONObject dataItem = dataFromServerJsonArray.getJSONObject(0);

            state = dataItem.getInt("fieldState");

        }catch(Exception e){
            e.printStackTrace();
        }

        return state;
    }

    public ArrayList<HashMap<String,String>> getPosts(){
        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("concepts");

            Log.d("cilo",""+dataFromServerJsonArray.length());

            if(dataFromServerJsonArray.length() == 0){
                dataFromServerArraylist = null;
            }else {
                for (int i = 0; i < dataFromServerJsonArray.length(); i++) {
                    JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);

                    String profileImg = dataItem.getString("profile_img");
                    String cartegoryName = dataItem.getString("cartegory_name");
                    String cartegoryIcon = dataItem.getString("cartegory_icon");
                    String name = dataItem.getString("name");
                    String conceptID = dataItem.getString("concept_id");
                    String contentType = dataItem.getString("content_type");
                    String username = dataItem.getString("username");
                    String postedDate = dataItem.getString("posted_date");
                    String views = dataItem.getString("views");

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("profile_img", profileImg);
                    hashMap.put("cartegory_name", cartegoryName);
                    hashMap.put("cartegory_icon", cartegoryIcon);
                    hashMap.put("name", name);
                    hashMap.put("concept_id", conceptID);
                    hashMap.put("content_type", contentType);
                    hashMap.put("username", username);
                    hashMap.put("posted_date", postedDate);
                    hashMap.put("views", views);

                    dataFromServerArraylist.add(hashMap);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return dataFromServerArraylist;
    }

    public  ArrayList<HashMap<String,String>> getCircles(){

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("circles");

            for(int i=0;i<dataFromServerJsonArray.length();i++){
                JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);

                String circleID = dataItem.getString("circle_id");
                String circleName = dataItem.getString("circle_name");
                String cartegoryName = dataItem.getString("cartegory_name");
                String members = dataItem.getString("members");
                String posts = dataItem.getString("posts");
                String username = dataItem.getString("username");

                dataFromServerHashMap = new HashMap<>();
                dataFromServerHashMap.put("circle_id",circleID);
                dataFromServerHashMap.put("circle_name",circleName);
                dataFromServerHashMap.put("cartegory_name",cartegoryName);
                dataFromServerHashMap.put("members",members);
                dataFromServerHashMap.put("posts",posts);
                dataFromServerHashMap.put("username",username);

                dataFromServerArraylist.add(dataFromServerHashMap);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return dataFromServerArraylist;
    }

    public int checkIfConceptIsPosted(){
        int state = -1;

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                state = -1;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("concepts");

            JSONObject dataItem = dataFromServerJsonArray.getJSONObject(0);

            state = dataItem.getInt("state");

        }catch(Exception e){
            e.printStackTrace();
        }

        return state;
    }

    public ArrayList<HashMap<String,String>> getCircleUsersConcepts(){
        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("userConcepts");

            Log.d("cilo",""+dataFromServerJsonArray.length());

            if(dataFromServerJsonArray.length() == 0){
                dataFromServerArraylist = null;
            }else {
                for (int i = 0; i < dataFromServerJsonArray.length(); i++) {

                    JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);
                    String user = dataItem.getString("user");
                    jsonObject = new JSONObject(user);
                    dataFromServerJsonArray = jsonObject.getJSONArray("concepts");

                    for(int j=0;j<dataFromServerJsonArray.length();j++){
                        JSONObject dataItem2 = dataFromServerJsonArray.getJSONObject(j);
                        String profileImg = dataItem2.getString("profile_img");
                        String cartegoryName = dataItem2.getString("cartegory_name");
                        String cartegoryIcon = dataItem2.getString("cartegory_icon");
                        String name = dataItem2.getString("name");
                        String conceptID = dataItem2.getString("concept_id");
                        String contentType = dataItem2.getString("content_type");
                        String username = dataItem2.getString("username");
                        String postedDate = dataItem2.getString("posted_date");
                        String views = dataItem2.getString("views");

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("profile_img", profileImg);
                        hashMap.put("cartegory_name", cartegoryName);
                        hashMap.put("cartegory_icon", cartegoryIcon);
                        hashMap.put("name", name);
                        hashMap.put("concept_id", conceptID);
                        hashMap.put("content_type", contentType);
                        hashMap.put("username", username);
                        hashMap.put("posted_date", postedDate);
                        hashMap.put("views", views);

                        dataFromServerArraylist.add(hashMap);

                        Log.d("cilo60",""+dataFromServerArraylist);
                        Log.d("cilo60","-"+username);
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        //Log.d("cilo60",""+dataFromServerArraylist);
        return dataFromServerArraylist;
    }

    public int getTotalUnreadNotifications(){
        int state = -1;

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                state = -1;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("notification");

            JSONObject dataItem = dataFromServerJsonArray.getJSONObject(0);

            state = dataItem.getInt("unread");

        }catch(Exception e){
            e.printStackTrace();
        }

        return state;
    }

    public ArrayList<HashMap<String,String>> getAllNotifications(){
        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                dataFromServerArraylist = null;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("notification");

            for(int i=0;i<dataFromServerJsonArray.length();i++){
                JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);

                String notificationID = dataItem.getString("notification_id");
                String source= dataItem.getString("source");
                String title = dataItem.getString("title");
                String message = dataItem.getString("message");
                String status = dataItem.getString("status");
                String postedDate = dataItem.getString("posted_date");
                String postedDateTwo = dataItem.getString("posted_date_two");

                dataFromServerHashMap = new HashMap<>();
                dataFromServerHashMap.put("notification_id",notificationID);
                dataFromServerHashMap.put("source",source);
                dataFromServerHashMap.put("title",title);
                dataFromServerHashMap.put("message",message);
                dataFromServerHashMap.put("status",status);
                dataFromServerHashMap.put("posted_date",postedDate);
                dataFromServerHashMap.put("posted_date_two",postedDateTwo);

                dataFromServerArraylist.add(dataFromServerHashMap);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("cilo65",""+dataFromServerArraylist);
        return dataFromServerArraylist;
    }

    public ArrayList<HashMap<String,String>> getMentors(){
        try{

            jsonObject = new JSONObject(response);
            if(jsonObject.length() == 0){
                dataFromServerJsonArray = null;
            }
            dataFromServerJsonArray = jsonObject.getJSONArray("userMentors");

            if(dataFromServerJsonArray.length() == 0) {
                dataFromServerJsonArray = null;
            }

            for(int i=0;i<dataFromServerJsonArray.length();i++){
                JSONObject dataItem = dataFromServerJsonArray.getJSONObject(i);
                //get mentor
                String userID = dataItem.getString("user_id");
                String username = dataItem.getString("username");
                String profImg = dataItem.getString("prof_img");
                String status = dataItem.getString("status");

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("user_id",userID);
                hashMap.put("username",username);
                hashMap.put("prof_img",profImg);
                hashMap.put("status",status);

                dataFromServerArraylist.add(hashMap);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("cilo90",""+dataFromServerArraylist);
        return dataFromServerArraylist;
    }

    public int checkIfMentorState(){
        int state = -1;

        try{
            jsonObject = new JSONObject(response);

            if(jsonObject.length() == 0){
                state = -1;
            }

            dataFromServerJsonArray = jsonObject.getJSONArray("mentor");
            JSONObject dataItem = dataFromServerJsonArray.getJSONObject(0);

            state = dataItem.getInt("state");

        }catch(Exception e){
            e.printStackTrace();
        }

        return state;
    }

}
