package com.example.foodreview;

import java.util.ArrayList;

public class University {
    private String name;
    private int id;
    private ArrayList<Restaurant> restaurants;

    University(int uniId, String uniName) {
        name = uniName;
        id = uniId;

    }

    String getUniName() {
        return name;
    }

    int getUniId() {
        return id;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }
    void setRestaurants(ArrayList<Restaurant> newRestaurants) {
        restaurants = newRestaurants;
    }
}
