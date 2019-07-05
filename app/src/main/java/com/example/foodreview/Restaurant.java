package com.example.foodreview;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String address;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getRestaurantName() {
        return name;
    }

    public String getRestaurantAddress() {
        return address;
    }
}
