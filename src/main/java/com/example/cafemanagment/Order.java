package com.example.cafemanagment;

public class Order {

    private int tableNumber;
    private String drinkName;
    private int quantity;
    private int price;

    public Order(int tableNumber, String drinkName, int quantity, int price) {
        this.tableNumber = tableNumber;
        this.drinkName = drinkName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
