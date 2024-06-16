package com.grpzerotwo.rentease;

import java.io.Serializable;

public class House implements Serializable {
    private String title;
    private String imageUrl;
    private String description;
    private String location;
    private int price;
    private int rooms;

    public House() {
        // Default constructor required for calls to DataSnapshot.getValue(House.class)
    }

    public House(String title, String imageUrl, String description, String location, int price, int rooms) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.location = location;
        this.price = price;
        this.rooms = rooms;
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
}
