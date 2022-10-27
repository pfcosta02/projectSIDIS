package com.example.project.model;

public class ReviewDTO {
    private String text = "";
    private float rating = 0;

    public ReviewDTO(String text, float rating) {
        this.text = text == null ? "" : text;
        this.rating = rating == 0.0f ? 0 : rating;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
