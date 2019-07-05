package com.example.foodreview;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private int id;
    private String[] address;
    private ArrayList<Food> foods;

    Restaurant(int resId, String resName, String[] newAddress) {
        name = resName;
        id = resId;
        address = newAddress;
    }

    public String getRestaurantName() {
        return name;
    }

    public int getRestaurantId() {
        return id;
    }

    void setRestaurantFoods(ArrayList<Food> newFoods) {
        this.foods = newFoods;
    }

    String[] getRestaurantAddress() {
        return address; }
}
