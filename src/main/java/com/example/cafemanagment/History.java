package com.example.cafemanagment;

public class History {
    int tableNumber;
    int funds;
    String date, time, drink;

    public History(int tableNumber, int funds, String drink, String date, String time) {
        this.tableNumber = tableNumber;
        this.funds = funds;
        this.date = date;
        this.time = time;
        this.drink = drink;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getFunds() {
        return funds;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDrink() {
        return drink;
    }
}
