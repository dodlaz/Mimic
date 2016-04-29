package com.example.dodlaz.mimic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyDB {
    private SQLiteDatabase database;

    public final static String HISCORE_TABLE = "Hiscore";

    public final static String P_ID = "id";
    public final static String DATE = "date";
    public final static String SCORE = "score";

    public MyDB(Context context) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insScore(int score) {
        ContentValues values = new ContentValues();
        values.put(DATE, getDateTime());
        values.put(SCORE, score + "");
        return database.insert(HISCORE_TABLE, null, values);
    }

    public Cursor getScore() {
        return database.rawQuery(
                "SELECT " + DATE + ", " + SCORE
                + " FROM " + HISCORE_TABLE
                + " ORDER BY " + SCORE + " DESC"
                , null
        );
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}