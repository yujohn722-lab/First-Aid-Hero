package com.example.myapplication;

public class KitItem {

    private String title;
    private String description;
    private boolean isExpanded;

    public KitItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.isExpanded = false;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    // Setter
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}