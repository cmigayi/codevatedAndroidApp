package com.example.cilo.codevated;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cilo on 4/21/17.
 */

public class Circle {
    int circle_id;
    String circleName;
    ArrayList<HashMap<String,String>> arrayList;


    public Circle(){

    }

    public Circle(ArrayList<HashMap<String,String>> arrayList){
        this.arrayList = arrayList;
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public ArrayList<HashMap<String, String>> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HashMap<String, String>> arrayList) {
        this.arrayList = arrayList;
    }
}
