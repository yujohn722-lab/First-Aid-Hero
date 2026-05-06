package com.example.myapplication;

public class KitItem {

    private String title;
    private String description;
    private String backgroundColor;
    private int iconRes;
    private boolean isExpanded;

    public KitItem(String title, String description, String backgroundColor, int iconRes) {
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.iconRes = iconRes;
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

    public int getIconRes() {
        return iconRes;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    // Setter
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
