package com.example.cafemanagment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.Tables;

import java.util.ArrayList;

public class TablesDB extends SQLiteOpenHelper {
    public TablesDB(Context context) {
        super(context, Constants.TABLES_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tables(id INTEGER PRIMARY KEY, funds INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tables");
        onCreate(db);
    }

    public void insertTable(int id, int funds){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("funds", funds);

        db.insert("tables", null, contentValues);
    }

    public void upateTableFunds(int id, int funds){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("funds", funds);

        db.update("tables", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTable(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tables", "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllTables(){
        SQLiteDatabase db = getWritableDatabase();

        db.rawQuery("delete from tables", null);
    }

    public ArrayList<Tables> getAllTables(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Tables> tables = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM tables", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int funds = cursor.getInt(cursor.getColumnIndex("funds"));

            tables.add(new Tables(id, funds));

            cursor.moveToNext();
        }

        return tables;
    }
}
