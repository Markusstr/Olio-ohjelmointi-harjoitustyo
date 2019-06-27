package com.example.foodreview;

import java.util.ArrayList;

public class University {
    private String name;
    private String id;
    ArrayList<University> uniList = new ArrayList<>();

    private static University university = new University();

    public static University getInstance() { return university; }

    private University() {

    }

    public String getUniName() {
        return name;
    }

    public String getUniId() {
        return id;
    }

    public void newUni(String newName, String newId) {
        University newUni = new University();
        newUni.name = newName;
        newUni.id = newId;
        uniList.add(newUni);
    }
}
