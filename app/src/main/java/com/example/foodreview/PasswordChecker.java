package com.example.foodreview;

public class PasswordChecker {

    private static PasswordChecker instance = null;

    //This class functions as a singleton

    private PasswordChecker() { }

    static PasswordChecker getInstance() {
        if (instance == null) {
            instance = new PasswordChecker();
        }
        else {
            System.out.println("Instance already exists.");
        }
        return instance;
    }

    //This method takes a password and runs checks. Returns string of error messages.
    //TODO: this method!
    String checker (String password) {
        return null;
    }

}
