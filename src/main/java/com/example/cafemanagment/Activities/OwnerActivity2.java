package com.example.cafemanagment.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cafemanagment.R;

public class OwnerActivity2 extends AppCompatActivity {

    Button addNewDrinkEditText, tablesButton, historyButton, ordersButton, changeUsernameButton, changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addNewDrinkEditText = findViewById(R.id.btn_add_drinks);
        tablesButton = findViewById(R.id.btn_tables);
        historyButton = findViewById(R.id.btn_history);
        ordersButton = findViewById(R.id.btn_orders);
        changeUsernameButton = findViewById(R.id.btn_change_name);
        changePasswordButton = findViewById(R.id.btn_change_pass);

        addNewDrinkEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity2.this, AddDrinkActivity.class);
                startActivity(intent);
            }
        });

        tablesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity2.this, TablesActivity.class);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity2.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity2.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity2.this, ChangeUsernameActivity.class);
                startActivity(intent);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity2.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
