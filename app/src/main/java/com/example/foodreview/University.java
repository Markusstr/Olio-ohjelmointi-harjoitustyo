package com.example.foodreview;

import java.util.ArrayList;

// University is a class that also functions as a manager for all restaurants.
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

    Restaurant getRestaurant (String name) {

        for (int x = 0; x < restaurants.size(); x++) {
            if (name.equals(restaurants.get(x).getRestaurantName())) {
                return restaurants.get(x);
            }
        }
        return null;
    }
}
