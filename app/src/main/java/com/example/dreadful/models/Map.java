package com.example.dreadful.models;

public class Map {
    private String name;
    private int status; // 0 - locked, 1 - unlocked
    private int image;
    private int explorePercentage;

    public Map(String name, int status, int image, int explorePercentage) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.explorePercentage = explorePercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getExplorePercentage() {
        return explorePercentage;
    }

    public void setExplorePercentage(int explorePercentage) {
        this.explorePercentage = explorePercentage;
    }
}
