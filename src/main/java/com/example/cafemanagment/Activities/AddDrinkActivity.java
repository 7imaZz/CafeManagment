package com.example.cafemanagment.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cafemanagment.DB.DrinksDB;
import com.example.cafemanagment.Drinks;
import com.example.cafemanagment.DrinksAdapter;
import com.example.cafemanagment.R;

import java.util.ArrayList;

public class AddDrinkActivity extends AppCompatActivity {

    DrinksDB db;
    ListView drinksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        db = new DrinksDB(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drinksListView = findViewById(R.id.lv_drinks);

        showAllDrinks();

        drinksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Drinks currentDrink = (Drinks) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddDrinkActivity.this);

                builder.setTitle("Edit Drink");

                LinearLayout linearLayout = new LinearLayout(AddDrinkActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final EditText input1 = new EditText(AddDrinkActivity.this);
                input1.setText(currentDrink.getName());
                input1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                linearLayout.addView(input1);

                final EditText input2 = new EditText(AddDrinkActivity.this);
                input2.setText(currentDrink.getPrice());
                input2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                input2.setInputType(InputType.TYPE_CLASS_NUMBER);
                linearLayout.addView(input2);

                builder.setView(linearLayout);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!input1.getText().toString().isEmpty() && !input2.getText().toString().isEmpty()){
                        db.updateDrink(currentDrink.getName(), input1.getText().toString(), input2.getText().toString());
                        showAllDrinks();
                        }else {
                            Toast.makeText(getApplicationContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        drinksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Drinks currentDrink = (Drinks) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddDrinkActivity.this);

                builder.setTitle("Confirmation")
                        .setMessage("Are You Sure You Want To Delete "+currentDrink.getName()+" From Drinks?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.deleteDrink(currentDrink.getName());
                                showAllDrinks();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drink_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }else if (id == R.id.add_new_drink) {
            addNewDrinkViaAlertMessage("Add New Drink", "Drink Name", "Drink Price","Add", "Cancel");
        }else if (id == R.id.del_all_drinks){

            AlertDialog.Builder builder = new AlertDialog.Builder(AddDrinkActivity.this);

            builder.setTitle("Confirmation")
                    .setMessage("Are You Sure You Want To Delete All Drinks?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteAllDrinks();
                            showAllDrinks();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void showAllDrinks(){

        ArrayList<Drinks> drinks = db.getAllDrinks();

        DrinksAdapter adapter = new DrinksAdapter(this, drinks);

        drinksListView.setAdapter(adapter);
    }

    public void addNewDrinkViaAlertMessage(String title, String hint1, String hint2, String positive, String negative){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText input1 = new EditText(this);
        input1.setHint(hint1);
        input1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linearLayout.addView(input1);

        final EditText input2 = new EditText(this);
        input2.setHint(hint2);
        input2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input2.setInputType(InputType.TYPE_CLASS_NUMBER);
        linearLayout.addView(input2);

        builder.setView(linearLayout);

        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!input1.getText().toString().isEmpty() && !input2.getText().toString().isEmpty()){
                db.insertDrink(input1.getText().toString(), input2.getText().toString());
                showAllDrinks();
                }else {
                    Toast.makeText(getApplicationContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }


}
