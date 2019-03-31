package com.example.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<History> {
    public HistoryAdapter(Context context, ArrayList<History> histories) {
        super(context, 0, histories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.history_list_item, parent, false);
        }

        History currentHistory = getItem(position);

        TextView tableNumberTextView = currentView.findViewById(R.id.tv_table_number_his);
        tableNumberTextView.setText(currentHistory.getTableNumber()+"");

        TextView fundsTextView = currentView.findViewById(R.id.tv_funds_his);
        fundsTextView.setText(currentHistory.getFunds()+"$");

        TextView drinkTextView = currentView.findViewById(R.id.tv_drink_tables_his);
        drinkTextView.setText(currentHistory.getDrink()+"");

        TextView dateTextView = currentView.findViewById(R.id.tv_date_his);
        dateTextView.setText(currentHistory.getDate());

        TextView timeTextView = currentView.findViewById(R.id.tv_time_his);
        timeTextView.setText(currentHistory.getTime());

        return currentView;
    }

}
