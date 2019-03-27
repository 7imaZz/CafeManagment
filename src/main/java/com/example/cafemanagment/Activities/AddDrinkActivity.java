package com.example.cafemanagment.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.cafemanagment.DB.DrinksDB;
import com.example.cafemanagment.Drinks;
import com.example.cafemanagment.DrinksAdapter;
import com.example.cafemanagment.R;

import java.util.ArrayList;

public class AddDrinkActivity extends AppCompatActivity {

    DrinksDB db = new DrinksDB(this);
    ListView drinksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drinksListView = findViewById(R.id.lv_drinks);

        showAllDrinks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }else if (id == R.id.add_new_drink){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add New Drink");

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final EditText input1 = new EditText(this);
            input1.setHint("Enter Drink Name");
            input1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(input1);

            final EditText input2 = new EditText(this);
            input2.setHint("Enter Price");
            input2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            input2.setInputType(InputType.TYPE_CLASS_NUMBER);
            linearLayout.addView(input2);

            builder.setView(linearLayout);

            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.insertDrink(input1.getText().toString(), input2.getText().toString());
                    showAllDrinks();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAllDrinks(){

        ArrayList<Drinks> drinks = db.getAllDrinks();

        DrinksAdapter adapter = new DrinksAdapter(this, drinks);

        drinksListView.setAdapter(adapter);
    }
}
