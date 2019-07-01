package com.example.foodreview;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String id;
    ArrayList<Restaurant> restaurantList = new ArrayList<>();

    private static Restaurant restaurant = new Restaurant();

    public static Restaurant getInstance() { return restaurant; }

    private Restaurant() {

    }

    public String getRestaurantName() {
        return name;
    }

    public String getRestaurantId() {
        return id;
    }

    public void newRestaurant(String newName, String newId) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.name = newName;
        newRestaurant.id = newId;
        restaurantList.add(newRestaurant);
    }
}
