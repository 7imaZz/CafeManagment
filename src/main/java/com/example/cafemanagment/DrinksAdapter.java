package com.example.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DrinksAdapter extends ArrayAdapter<Drinks> {
    public DrinksAdapter(Context context, ArrayList<Drinks> drinks) {
        super(context, 0, drinks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.drink_list_item, parent, false);
        }

        Drinks drinks = getItem(position);

        TextView nameTextView = currentView.findViewById(R.id.tv_drink_name);
        nameTextView.setText(drinks.getName());

        TextView priceTextView = currentView.findViewById(R.id.tv_drink_price);
        priceTextView.setText(drinks.getPrice()+"$");

        TextView dateTextView = currentView.findViewById(R.id.tv_date);
        dateTextView.setText(drinks.getDate());

        TextView timeTextView = currentView.findViewById(R.id.tv_time);
        timeTextView.setText(drinks.getTime());

        return currentView;
    }
}
