package com.example.foodreview;

import java.util.ArrayList;

public class Food {
    private String name;
    private String id;
    private double price;
    ArrayList<Food> foodList = new ArrayList<>();

    private static Food food = new Food();

    static Food getInstance() { return food; }

    private Food() {

    }

    String getFoodName() {
        return name;
    }

    public String getFoodId() {
        return id;
    }

    double getFoodPrice() {
        return price;
    }

    void newFood(String newName, String newId, double newPrice) {
        Food newFood = new Food();
        newFood.name = newName;
        newFood.id = newId;
        newFood.price = newPrice;
        foodList.add(newFood);
    }
}
