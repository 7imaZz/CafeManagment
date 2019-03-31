package com.example.cafemanagment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanagment.Constants;

public class LoginDB extends SQLiteOpenHelper {


    public LoginDB(Context context) {
        super(context, Constants.LOGIN_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE login(id INTEGER PRIMARY KEY, user TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);
    }

    public void insert(String user, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("user", user);
        contentValues.put("password", password);

        db.insert("login", null, contentValues);
    }

    public void changeUser(String user){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("user", user);

        db.update("login", contentValues, "id = ?", new String[]{"1"});
    }

    public void changePass(String pass){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("password", pass);

        db.update("login", contentValues, "id = ?", new String[]{"1"});
    }
}
