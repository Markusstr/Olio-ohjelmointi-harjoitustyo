package com.example.foodreview;


class Review {
    private int reviewId;
    private int foodId;
    private String review;
    private float grade;
    private String userId;
    private String foodName;

    Review(int newReviewId, int newFoodId, float newStars, String newReview, String newUserId) {
        reviewId = newReviewId;
        foodId = newFoodId;
        grade = newStars;
        userId = newUserId;
        review = newReview;
        foodName = null;
    }

    String getReview() {
        return review;
    }

    void setFoodName(String newFoodName) {
        foodName = newFoodName;
    }

    int getFoodId() { return foodId; }

    int getReviewId() {
        return reviewId;
    }

    float getGrade() {
        return grade;
    }

    String getUserId() {
        return userId;
    }

}
