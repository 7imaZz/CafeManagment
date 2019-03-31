package com.example.cafemanagment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cafemanagment.Constants;
import com.example.cafemanagment.Order;

import java.util.ArrayList;

public class OrdersDB extends SQLiteOpenHelper {
    public OrdersDB(Context context) {

        super(context, Constants.ORDERS_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE orders(id INTEGER, name TEXT, price INTEGER, quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    public void insertOrder(int id, String name, int price, int quantity){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("quantity", quantity);

        db.insert("orders", null, contentValues);
    }

    public void deleteOrder(int id){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("orders", "id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList <Order> getAllOrders(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList <Order> orders = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM orders", null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            int number = cursor.getInt(cursor.getColumnIndex("id"));
            String drink = cursor.getString(cursor.getColumnIndex("name"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));

            orders.add(new Order(number, drink, quantity, price));
            cursor.moveToNext();
        }

        return orders;
    }
}
