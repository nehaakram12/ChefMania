package com.app.chefmania.chefmania.Model;

public class FoodMenu {
    String id;
    String name;
    String dish_type;
    int price;
    String prep_time;
    int availability;

    public FoodMenu() {
    }

    public FoodMenu(String id, String name, String dish_type, int price, String prep_time, int availability) {
        this.id = id;
        this.name = name;
        this.dish_type = dish_type;
        this.price = price;
        this.prep_time = prep_time;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(String prep_time) {
        this.prep_time = prep_time;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
