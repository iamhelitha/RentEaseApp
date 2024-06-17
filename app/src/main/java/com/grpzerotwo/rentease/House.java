package com.grpzerotwo.rentease;

public class House {

    private String id;
    private String title;
    private String description;
    private String location;
    private int price;
    private int rooms;
    private String imageUrl;

    public House() {
        // Default constructor required for calls to DataSnapshot.getValue(House.class)
    }

    public House(String title, String description, String location, int price, int rooms, String imageUrl) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.rooms = rooms;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
