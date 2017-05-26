package com.example.cilo.codevated;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by cilo on 5/8/17.
 */

public class LocaluserInterestDatabase extends SQLiteOpenHelper {

    public LocaluserInterestDatabase(Context context, String name,
                                SQLiteDatabase.CursorFactory factory,
                                int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE userInterest(" +
                "userInterest_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "interest_id INTEGER NOT NULL," +
                "interest_cart TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST userInterest");
        onCreate(db);
    }

    public void insertData(int interestID, String interestCart){
        ContentValues values = new ContentValues();
        values.put("interest_id",interestID);
        values.put("interest_cart",interestCart);
        this.getWritableDatabase().insertOrThrow("userInterest","",values);
    }

    public UserInterest getData(){
        UserInterest userInterest = null;

        Cursor cursor = this.getReadableDatabase().
                rawQuery("SELECT * FROM userInterest",null);

        while(cursor.moveToNext()){
            int userInterestID = cursor.getInt(0);
            int interestID = cursor.getInt(1);
            String interestCart = cursor.getString(2);

            userInterest = new UserInterest(userInterestID,interestID,interestCart);
        }

        return userInterest;
    }

    public void deleteData(int userInterestID){

        this.getWritableDatabase().delete("userInterest",
                "userInterest_id="+userInterestID,null);
    }

    public void updateTitleData(int userInterestID, int interestID){

        this.getWritableDatabase().
                execSQL("UPDATE userInterest SET interest_id='"+interestID+"' WHERE userInterest_id='"+userInterestID+"'");
    }

    public void updateContentData(int userInterestID, String interestCart){

        this.getWritableDatabase().
                execSQL("UPDATE userInterest SET interest_cart='"+interestCart+"' WHERE userInterest_id='"+userInterestID+"'");
    }
}
