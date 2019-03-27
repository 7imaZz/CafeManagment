package com.example.cafemanagment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.Drinks;

import java.util.ArrayList;

public class DrinksDB extends SQLiteOpenHelper{



    public DrinksDB(Context context) {
        super(context, Constants.DRINKS_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE drinks (name TEXT, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS drinks");
        onCreate(db);
    }

    public void insertDrink(String name, String price){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("price", price);

        db.insert("drinks", null, contentValues);
    }

    public ArrayList <Drinks> getAllDrinks(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Drinks> drinks = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM drinks", null);

        cursor.moveToFirst();

        while(cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String price = cursor.getString(cursor.getColumnIndex("price"));

            drinks.add(new Drinks(name, price));
        }

        return drinks;
    }
}
