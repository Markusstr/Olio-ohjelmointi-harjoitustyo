package com.example.foodreview;

public class Food {
    private String name;
    private String id;
    private double price;

    public Food(String newName, String newId, double newPrice) {
        name = newName;
        id = newId;
        price = newPrice;
    }

    public String getFoodName() {
        return name;
    }

    public String getFoodId() {
        return id;
    }

    public double getFoodPrice() {
        return price;
    }
}
