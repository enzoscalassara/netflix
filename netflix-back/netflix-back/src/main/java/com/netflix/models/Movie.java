package com.netflix.models;

public class Movie {
    private String title;
    private String description;
    private int ageRating;

    public Movie(String title, String description, int ageRating) {
        this.title = title;
        this.description = description;
        this.ageRating = ageRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }
}
