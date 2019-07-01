package com.example.foodreview;

import java.util.ArrayList;

public class Review {
    private String id;
    private String review;
    private int grade;
    private String reviewer;
    ArrayList<Review> reviewList = new ArrayList<>();

    private Review() {

    }

    public String getReview() {
        return review;
    }

    public String getReviewId() {
        return id;
    }

    public int getGrade() {
        return grade;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void newReview(String newId, String newReviewText, int newGrade, String newReviewer) {
        Review newReview = new Review();
        newReview.review = newReviewText;
        newReview.id = newId;
        newReview.grade = newGrade;
        newReview.reviewer = newReviewer;
        reviewList.add(newReview);
    }
}
