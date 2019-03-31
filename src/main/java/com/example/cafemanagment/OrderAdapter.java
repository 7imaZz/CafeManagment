package com.example.cafemanagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    public OrderAdapter(Context context, ArrayList <Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = convertView;

        if (currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.order_list_item, parent, false);
        }

        Order currentOrder = getItem(position);

        TextView tableNumberTextView = currentView.findViewById(R.id.tv_table_number_order);
        tableNumberTextView.setText(currentOrder.getTableNumber()+"");

        TextView tableDrinkNameTextView = currentView.findViewById(R.id.tv_drink_name_order);
        tableDrinkNameTextView.setText(currentOrder.getDrinkName());

        TextView priceTextView = currentView.findViewById(R.id.tv_price_order);
        priceTextView.setText(currentOrder.getPrice()+"$");

        TextView quantityTextView = currentView.findViewById(R.id.tv_quantity_order);
        quantityTextView.setText(currentOrder.getQuantity()+"");

        return currentView;
    }
}
