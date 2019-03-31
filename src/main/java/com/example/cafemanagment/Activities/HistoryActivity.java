package com.example.cafemanagment.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cafemanagment.DB.HistoryDB;
import com.example.cafemanagment.History;
import com.example.cafemanagment.HistoryAdapter;
import com.example.cafemanagment.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView historyListView;
    TextView noDataTextView;
    HistoryDB historyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historyDB = new HistoryDB(this);

        historyListView = findViewById(R.id.lv_histories);
        noDataTextView = findViewById(R.id.tv_no_history);

        historyListView.setEmptyView(noDataTextView);

        showAllHistories();
    }

    public void showAllHistories(){

        ArrayList<History> histories = historyDB.getAllHistories();

        HistoryAdapter adapter = new HistoryAdapter(this, histories);

        historyListView.setAdapter(adapter);
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
