package com.example.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        return currentView;
    }
}
