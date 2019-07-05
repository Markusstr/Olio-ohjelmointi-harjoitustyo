package com.example.foodreview;

class Food {
    private String name;
    private int id;
    private float price;
    private String date;

    Food(String newName, int newId, float newPrice, String newDate) {
        name = newName;
        id = newId;
        price = newPrice;
        date = newDate;

    }

    String getFoodName() {
        return name;
    }

    int getFoodId() {
        return id;
    }

    float getFoodPrice() {
        return price;
    }

    String getDate() {
        return date;
    }


}
