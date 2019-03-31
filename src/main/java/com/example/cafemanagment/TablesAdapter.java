package com.example.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TablesAdapter extends ArrayAdapter<Tables> {
    public TablesAdapter(Context context, ArrayList<Tables> tables) {
        super(context, 0, tables);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.tables_list_item, parent, false);
        }

        Tables currentTable = getItem(position);

        TextView tableNumberTextView = currentView.findViewById(R.id.tv_table_number_tables);
        tableNumberTextView.setText(currentTable.getTableNumber()+"");

        TextView tableFundsTextView = currentView.findViewById(R.id.tv_funds_tables);
        tableFundsTextView.setText(currentTable.getFunds()+"$");

        return currentView;
    }
}
