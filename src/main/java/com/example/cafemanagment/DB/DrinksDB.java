package com.example.cafemanagment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.Drinks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DrinksDB extends SQLiteOpenHelper{



    public DrinksDB(Context context) {
        super(context, Constants.DRINKS_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE drinks (name TEXT, price TEXT, date TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS drinks");
        onCreate(db);
    }

    public void insertDrink(String name, String price){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String [] str = timeStamp.split(" ");
        String date = str[0];
        String time = str[1];

        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("date", date);
        contentValues.put("time", time);

        db.insert("drinks", null, contentValues);
    }

    public void updateDrink(String oldName, String newName, String price){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", newName);
        contentValues.put("price", price);

        db.update("drinks", contentValues, "name=?", new String[]{oldName});
    }

    public void deleteDrink(String name){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("drinks", "name=?", new String[]{name});
    }

    public void deleteAllDrinks(){
        SQLiteDatabase db = getWritableDatabase();

        db.rawQuery("delete from drinks", null);
    }

    public ArrayList <Drinks> getAllDrinks(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Drinks> drinks = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM drinks", null);

        cursor.moveToFirst();

        while(cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String time = cursor.getString(cursor.getColumnIndex("time"));

            drinks.add(new Drinks(name, price, date, time));
        }

        return drinks;
    }


}
