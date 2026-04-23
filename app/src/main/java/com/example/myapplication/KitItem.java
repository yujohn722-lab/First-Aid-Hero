package com.example.myapplication;

public class KitItem {

    private String title;
    private String description;
    private String backgroundColor;
    private boolean isExpanded;

    public KitItem(String title, String description, String backgroundColor) {
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.isExpanded = false;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    // Setter
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}