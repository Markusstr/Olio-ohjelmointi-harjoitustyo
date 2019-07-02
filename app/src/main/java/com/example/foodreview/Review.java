package com.example.foodreview;

import java.util.ArrayList;

public class Review {
    private String id;
    private String review;
    private float grade;
    private String reviewer;
    ArrayList<Review> reviewList = new ArrayList<>();

    private static Review reviewInstance = new Review();

    public static Review getInstance() { return reviewInstance; }

    private Review() {

    }

    public String getReview() {
        return review;
    }

    public String getReviewId() {
        return id;
    }

    public float getGrade() {
        return grade;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void newReview(String newId, String newReviewText, float newGrade, String newReviewer) {
        Review newReview = new Review();
        newReview.review = newReviewText;
        newReview.id = newId;
        newReview.grade = newGrade;
        newReview.reviewer = newReviewer;
        reviewList.add(newReview);
    }
}
