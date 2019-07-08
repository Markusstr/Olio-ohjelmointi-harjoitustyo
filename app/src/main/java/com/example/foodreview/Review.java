package com.example.foodreview;


public class Review {
    private int id;
    private String review;
    private float grade;
    private String userId;
    private int foodId;

    private Review(float newStars, String newReview, String newUserId, int newFoodId) {
        grade = newStars;
        userId = newUserId;
        review = newReview;
        foodId = newFoodId;
    }

    public String getReview() {
        return review;
    }

    public int getReviewId() {
        return id;
    }

    public float getGrade() {
        return grade;
    }

    public String getUserId() {
        return userId;
    }

}
