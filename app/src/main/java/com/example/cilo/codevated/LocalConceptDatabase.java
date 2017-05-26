package com.example.cilo.codevated;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by cilo on 3/14/17.
 */

public class LocalConceptDatabase extends SQLiteOpenHelper {

    public LocalConceptDatabase(Context context, String name,
                                SQLiteDatabase.CursorFactory factory,
                                int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE concept(" +
                "concept_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST concept");
        onCreate(db);
    }

    public void insertData(String title, String content){
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        this.getWritableDatabase().insertOrThrow("concept","",values);
    }

    public ArrayList<String> getData(){
        Cursor cursor = this.getReadableDatabase().
                rawQuery("SELECT * FROM concept",null);

        ArrayList<String> arrayList = new ArrayList<String>();

        while(cursor.moveToNext()){
            int conceptID = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);

            arrayList.add(conceptID+","+title+","+content);
        }

        return arrayList;
    }

    public Concept getConceptData(){
        Concept concept = null;
        Cursor cursor = this.getReadableDatabase().
                rawQuery("SELECT * FROM concept WHERE concept_id='1'",null);

        ArrayList<String> arrayList = new ArrayList<String>();

        while(cursor.moveToNext()){
            int conceptID = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);

            concept = new Concept(conceptID,title,content);
        }

        return concept;
    }

    public void deleteData(int contentID){

        this.getWritableDatabase().delete("concept",
                "concept_id="+contentID,null);
    }

    public void updateTitleData(int contentID, String newTitle){

        this.getWritableDatabase().
                execSQL("UPDATE concept SET title='"+newTitle+"' WHERE concept_id='"+contentID+"'");
    }

    public void updateContentData(int contentID, String newContent){

        this.getWritableDatabase().
                execSQL("UPDATE concept SET content='"+newContent+"' WHERE concept_id='"+contentID+"'");
    }
}
