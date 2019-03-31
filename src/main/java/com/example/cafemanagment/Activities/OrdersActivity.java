package com.example.cafemanagment.Activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanagment.DB.HistoryDB;
import com.example.cafemanagment.DB.OrdersDB;
import com.example.cafemanagment.DB.TablesDB;
import com.example.cafemanagment.Order;
import com.example.cafemanagment.OrderAdapter;
import com.example.cafemanagment.R;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    ListView ordersListView;
    TextView emptyTextView;
    OrdersDB ordersDB;
    HistoryDB historyDB;
    TablesDB tablesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ordersDB = new OrdersDB(this);
        historyDB = new HistoryDB(this);
        tablesDB = new TablesDB(this);

        ordersListView = findViewById(R.id.lv_order);
        emptyTextView = findViewById(R.id.tv_empty_order);

        ordersListView.setEmptyView(emptyTextView);

        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getItemAtPosition(position);

                showDialog(order);
            }
        });
        showAllOrders();
    }

    public void showAllOrders(){

        ArrayList<Order> orders = ordersDB.getAllOrders();

        OrderAdapter adapter = new OrderAdapter(this, orders);

        ordersListView.setAdapter(adapter);
    }

    public void showDialog(final Order order){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Make Decision")
                .setMessage("What do you want to do with this order?")
                .setPositiveButton("Served", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int funds = -1;
                        historyDB.insertHistory(order.getTableNumber(), order.getPrice(), order.getDrinkName());

                        Cursor cursor = tablesDB.getReadableDatabase().rawQuery("select * from tables where id = "+order.getTableNumber(), null);
                        if (cursor != null){
                            if (cursor.moveToFirst()){
                                funds = cursor.getInt(cursor.getColumnIndex("funds"));
                            }

                        }
                        tablesDB.upateTableFunds(order.getTableNumber(), funds+order.getPrice());
                        ordersDB.deleteOrder(order.getTableNumber());
                        Toast.makeText(getApplicationContext(), "Order served", Toast.LENGTH_SHORT).show();
                        showAllOrders();
                    }
                }).setNegativeButton("Cancel Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ordersDB.deleteOrder(order.getTableNumber());
                Toast.makeText(getApplicationContext(), "Order deleted", Toast.LENGTH_SHORT).show();
                showAllOrders();
            }
        }).create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
