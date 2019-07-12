package com.example.foodreview;

import android.content.Context;
import java.io.FileWriter;
import java.io.IOException;

public class PrintToFile {

    private static PrintToFile instance = null;

    private PrintToFile() {

    }

    static PrintToFile getInstance() {
        if (instance == null) {
            instance = new PrintToFile();
        }
        else {
            System.out.println("Instance already exists.");
        }
        return instance;
    }

    void executePrint(Context context, University university) {
        try {
            FileWriter csvWriter = new FileWriter(context.getFilesDir() + university.getUniName() + "Data.csv");
            csvWriter.append(university.getUniName());
            for (Restaurant restaurant : university.getRestaurants()) {
                String restaurantName = restaurant.getRestaurantName() + ",";
                csvWriter.append(restaurantName);
                String restaurantAddress = restaurant.getRestaurantAddress() + ",";
                csvWriter.append(restaurantAddress);
                csvWriter.append(";");
                for (Food food : restaurant.getFoods()) {
                    String foodName = food.getFoodName() + ",";
                    csvWriter.append(foodName);
                    String price = food.getFoodPrice() + "€";
                    csvWriter.append(price);
                    csvWriter.append(";");
                    for (Review review : food.getReviews()) {
                        String grade = review.getGrade() + " / 5.0,";
                        csvWriter.append(grade);
                        csvWriter.append(review.getReview());
                        csvWriter.append(";");
                    }

                }
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
