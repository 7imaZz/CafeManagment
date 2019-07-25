package com.example.cafemanagment.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanagment.DB.DrinksDB;
import com.example.cafemanagment.DB.HistoryDB;
import com.example.cafemanagment.DB.OrdersDB;
import com.example.cafemanagment.DB.TablesDB;
import com.example.cafemanagment.Drinks;
import com.example.cafemanagment.R;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    boolean calcClicked = false;
    EditText tableNumberEditText;
    Spinner drinksSpinner;
    TextView quantityTextView, billTextView;
    Button incButton, decButton,  calculateButton, orderButton;
    int quantity = 0;
    int price = 0;
    int tableNumbers;
    ArrayList<String> spinnerDrinks = new ArrayList<>();
    DrinksDB db = new DrinksDB(this);
    TablesDB tablesDB;
    OrdersDB ordersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        tablesDB = new TablesDB(this);
        ordersDB = new OrdersDB(this);

        tableNumbers = tablesDB.getAllTables().size();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tableNumberEditText = findViewById(R.id.ed_table_number);

        drinksSpinner = findViewById(R.id.spinner);

        quantityTextView = findViewById(R.id.tv_quantity);
        billTextView = findViewById(R.id.tv_bill);

        incButton = findViewById(R.id.btn_inc);
        decButton = findViewById(R.id.btn_dec);
        calculateButton = findViewById(R.id.btn_calc);
        orderButton = findViewById(R.id.btn_order);

        spinnerDrinks.add(0, "Select Your Drink");


        ArrayList <Drinks> drinks = db.getAllDrinks();

        for (int i=0; i<drinks.size(); i++){
            spinnerDrinks.add(drinks.get(i).getName()+" "+drinks.get(i).getPrice()+"$");
        }
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerDrinks);
        drinksSpinner.setAdapter(adapter);

        quantityTextView.setText(""+quantity);

        tableNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });

        drinksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position>0) {
                    String str = (String) parent.getItemAtPosition(position);
                    String numberOnly= str.replaceAll("[^0-9]", "");
                    price = Integer.parseInt(numberOnly);
                }
                if (calcClicked)
                    updateBill();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quantityTextView.setText(""+quantity);
                if (calcClicked)
                    updateBill();
            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>0)
                    quantity--;
                quantityTextView.setText(""+quantity);
                if (calcClicked)
                    updateBill();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean in = false;

                if (tableNumberEditText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Table ID", Toast.LENGTH_SHORT).show();
                }else{

                    for (int i = 0; i<tablesDB.getAllTables().size(); i++){
                        if (Integer.parseInt(tableNumberEditText.getText().toString()) == tablesDB.getAllTables().get(i).getTableNumber()){
                            in = true;
                        }
                    }
                    if (tableNumberEditText.getText().toString().isEmpty() || in == false){
                        Toast.makeText(getApplicationContext(), "Please Enter Valid Table Number!", Toast.LENGTH_SHORT).show();
                    }else if (drinksSpinner.getSelectedItemPosition() == 0){
                        Toast.makeText(getApplicationContext(), "Please Choose Drink!", Toast.LENGTH_SHORT).show();
                    }else if (quantity == 0){
                        Toast.makeText(getApplicationContext(), "Please Define The Quantity That You Need!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        calcClicked = true;
                        updateBill();
                    }
                }
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean in = false;



                if (tableNumberEditText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Table ID", Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i<tablesDB.getAllTables().size(); i++){
                        if (Integer.parseInt(tableNumberEditText.getText().toString()) == tablesDB.getAllTables().get(i).getTableNumber()){
                            in = true;
                        }
                    }
                }
                if (tableNumberEditText.getText().toString().isEmpty() || in == false){
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Table Number!", Toast.LENGTH_SHORT).show();
                }else if (drinksSpinner.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Please Choose Drink!", Toast.LENGTH_SHORT).show();
                }else if (quantity == 0){
                    Toast.makeText(getApplicationContext(), "Please Define The Quantity That You Need!", Toast.LENGTH_SHORT).show();
                } else {

                    boolean thereIsAnOrder = false;
                    for (int i = 0; i < ordersDB.getAllOrders().size(); i++) {
                        if (ordersDB.getAllOrders().get(i).getTableNumber() == Integer.parseInt(tableNumberEditText.getText().toString())) {
                            thereIsAnOrder = true;
                        }
                    }
                    if (thereIsAnOrder) {
                        Toast.makeText(getApplicationContext(), "There is an order not served yet for this table!", Toast.LENGTH_LONG).show();
                    } else {
                        ordersDB.insertOrder(Integer.parseInt(tableNumberEditText.getText().toString()),
                                spinnerDrinks.get(drinksSpinner.getSelectedItemPosition()).replace("$", "").replaceAll("[0-9]", ""), price * quantity, quantity);
                        finish();
                    }
                }
            }
        });

    }



    public void updateBill(){
        billTextView.setText("Table Number: "+tableNumberEditText.getText().toString()+
                "\nDrink: "+spinnerDrinks.get(drinksSpinner.getSelectedItemPosition()).replace("$", "").replaceAll("[0-9]","")+
                "\nQuantity: "+quantity+
                "\nTotal Price: "+price*quantity+"$"+
                "\nThank You!");
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
