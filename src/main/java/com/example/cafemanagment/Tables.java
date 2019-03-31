package com.example.cafemanagment;

public class Tables {
    int tableNumber;
    int funds;
    String date, time;

    public Tables(int tableNumber, int funds, String date, String time) {
        this.tableNumber = tableNumber;
        this.funds = funds;
        this.date = date;
        this.time = time;
    }

    public Tables(int tableNumber, int funds) {
        this.tableNumber = tableNumber;
        this.funds = funds;
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

}

