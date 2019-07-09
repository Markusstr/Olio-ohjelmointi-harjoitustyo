package com.example.foodreview;

public class User {
    private String username;
    private boolean isAdmin;

    User (String newUsername, int newIsAdmin) {
        username = newUsername;
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
}
