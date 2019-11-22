package com.app.chefmania.chefmania.Model;

public class Inventory {
    String name;
    int price;
    int quantity;
    int availability;
    int threshold;
    String expiry_date;

    public Inventory() {
    }

    public Inventory(String name, int price, int quantity, int availability, int threshold, String expiry_date) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.availability = availability;
        this.threshold=threshold;
        this.expiry_date = expiry_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getExpirydate() {
        return expiry_date;
    }

    public void setExpirydate(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
