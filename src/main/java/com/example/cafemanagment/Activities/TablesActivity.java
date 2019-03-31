package com.example.cafemanagment.Activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafemanagment.DB.TablesDB;
import com.example.cafemanagment.R;
import com.example.cafemanagment.Tables;
import com.example.cafemanagment.TablesAdapter;

import java.util.ArrayList;

public class TablesActivity extends AppCompatActivity {

    TablesDB db;
    ListView tablesListView;
    EditText numberOfTablesEditText;
    Cursor cursor;
    TextView totalTextView;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new TablesDB(this);

        cursor = db.getReadableDatabase().rawQuery("select * from tables", null);
        cursor.moveToLast();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tablesListView = findViewById(R.id.lv_tables);
        numberOfTablesEditText = findViewById(R.id.ed_number_of_tables);

        tablesListView.setEmptyView(numberOfTablesEditText);
        totalTextView = findViewById(R.id.tv_total);

        if (db.getAllTables().size() == 0)
            totalTextView.setVisibility(View.GONE);



        numberOfTablesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    for (int i = 1; i<= Integer.parseInt(numberOfTablesEditText.getText().toString()); i++){
                        db.insertTable(i, 0);
                    }
                    showAllTable();
                }
                return false;
            }
        });

        tablesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Tables table = (Tables) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(TablesActivity.this);

                builder.setTitle("DELETE")
                        .setMessage("Are you sure you want to delete this table?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteTable(table.getTableNumber());
                                Toast.makeText(getApplicationContext(), "Table deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return false;
            }
        });

        total = calcTotal();
        totalTextView.setText("Total = "+total+"$");
        showAllTable();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        cursor = db.getReadableDatabase().rawQuery("select * from tables", null);
        cursor.moveToLast();


        int id = item.getItemId();

        if (id == android.R.id.home){
          finish();
        }else if (id == R.id.add_new_table){
            db.insertTable(cursor.getInt(cursor.getColumnIndex("id"))+1, 0);
            Toast.makeText(getApplicationContext(), "Table Added Succefully", Toast.LENGTH_SHORT).show();
            showAllTable();
        }else if (id == R.id.reset_funds){
            for (int i=0; i<db.getAllTables().size(); i++){
                db.upateTableFunds(db.getAllTables().get(i).getTableNumber(), 0);
            }

            totalTextView.setText("Total = "+0+"$");
            showAllTable();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showAllTable(){

        ArrayList<Tables> tables = db.getAllTables();

        TablesAdapter adapter = new TablesAdapter(this, tables);

        tablesListView.setAdapter(adapter);
    }

    public int calcTotal(){
        int tot = 0;
        for (int i=0; i<db.getAllTables().size(); i++){
            tot += db.getAllTables().get(i).getFunds();
        }

        return tot;
    }


}
