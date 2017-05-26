package com.example.cilo.codevated;

/**
 * Created by cilo on 5/8/17.
 */

public class UserInterest {
    int userInterestID, interestID;
    String interestCart;

    public UserInterest(int userInterestID, int interestID, String interestCart) {
        this.userInterestID = userInterestID;
        this.interestID = interestID;
        this.interestCart = interestCart;
    }

    public int getUserInterestID() {
        return userInterestID;
    }

    public void setUserInterestID(int userInterestID) {
        this.userInterestID = userInterestID;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }

    public String getInterestCart() {
        return interestCart;
    }

    public void setInterestCart(String interestCart) {
        this.interestCart = interestCart;
    }
}
