package com.example.cafemanagment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoryDB extends SQLiteOpenHelper {



    public HistoryDB(Context context) {
        super(context, Constants.History_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE history(id INTEGER, funds INTEGER, drink TEXT, date TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }

    public void insertHistory(int id, int funds, String drink){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("funds", funds);
        contentValues.put("drink", drink);

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String [] str = timeStamp.split(" ");
        String date = str[0];
        String time = str[1];

        contentValues.put("date", date);
        contentValues.put("time", time);

        db.insert("history", null, contentValues);
    }

    public ArrayList<History> getAllHistories(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<History> histories = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM history", null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int funds = cursor.getInt(cursor.getColumnIndex("funds"));
            String drink = cursor.getString(cursor.getColumnIndex("drink"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String time = cursor.getString(cursor.getColumnIndex("time"));

            histories.add(new History(id, funds, drink, date, time));
            cursor.moveToNext();
        }

        return histories;
    }
}
