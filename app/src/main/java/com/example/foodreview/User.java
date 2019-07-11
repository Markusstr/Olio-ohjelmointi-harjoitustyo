package com.example.foodreview;

class User {
    private String username;
    private boolean isAdmin;
    private int homeUniId;

    User (String newUsername, int newIsAdmin, int newHomeUniId) {
        username = newUsername;
        homeUniId = newHomeUniId;
        if (newIsAdmin == 1) {
            isAdmin = true;
        }
        else if (newIsAdmin == 0) {
            isAdmin = false;
        }
    }

    String getUsername (){
        return username;
    }
    boolean getIsAdmin (){
        return isAdmin;
    }
    int getHomeUniId() {
        return homeUniId;
    }
}
