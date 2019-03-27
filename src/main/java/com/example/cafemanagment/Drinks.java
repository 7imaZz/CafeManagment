package com.example.cafemanagment;

public class Drinks {
    private String name;
    private String price;
    private String date;
    private String time;

    public Drinks(String name, String price, String date, String time) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public Drinks(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
