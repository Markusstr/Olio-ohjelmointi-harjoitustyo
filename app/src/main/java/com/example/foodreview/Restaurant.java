package com.example.foodreview;

public class Restaurant {
    private String name;
    private String id;

    public Restaurant(String newName, String newId) {
        name = newName;
        id = newId;
    }

    public String getRestaurantName() {
        return name;
    }

    public String getRestaurantId() {
        return id;
    }
}
