package com.example.myapplication;

public class CarouselItem {
    private String backgroundColor;
    private int imageResource;
    private String title;
    private String text;

    public CarouselItem(String backgroundColor, int imageResource, String title, String text) {
        this.backgroundColor = backgroundColor;
        this.imageResource = imageResource;
        this.title = title;
        this.text = text;
    }

    public String getBackgroundColor() { return backgroundColor; }
    public int getImageResource() { return imageResource; }
    public String getTitle() { return title; }
    public String getText() { return text; }
}