package com.example.foodreview;


public class Review {
    private int reviewId;
    private String review;
    private float grade;
    private String userId;

    Review(int newReviewId, float newStars, String newReview, String newUserId) {
        reviewId = newReviewId;
        grade = newStars;
        userId = newUserId;
        review = newReview;
    }

    public String getReview() {
        return review;
    }

    public int getReviewId() {
        return reviewId;
    }

    public float getGrade() {
        return grade;
    }

    public String getUserId() {
        return userId;
    }

}
