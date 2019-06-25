package com.example.foodreview;

public class Review {
    private String id;
    private String review;
    private int grade;
    private String reviewer;

    public Review(String newId, String newReview, int newGrade, String newReviewer) {
        id = newId;
        review = newReview;
        grade = newGrade;
        reviewer = newReviewer;
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
}
