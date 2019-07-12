package com.example.foodreview;

public class PrintToFile {

    private PrintToFile instance = null;

    private PrintToFile() {

    }

    PrintToFile getInstance() {
        if (instance == null) {
            instance = new PrintToFile();
        }
        else {
            System.out.println("Instance already exists.");
        }
        return instance;
    }

    void executePrint(University university) {

    }
}
