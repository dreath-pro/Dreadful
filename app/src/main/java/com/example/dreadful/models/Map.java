package com.example.dreadful.models;

public class Map {
    private int id;
    private String name;
    private int status; // 0 - locked, 1 - unlocked
    private int image;
    private int explorePercentage;
    private String requirements;

    public Map(String name, int status, int image, int explorePercentage, String requirements) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.explorePercentage = explorePercentage;
        this.requirements = requirements;
    }

    public Map(int id, String name, int status, int image, int explorePercentage, String requirements) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
        this.explorePercentage = explorePercentage;
        this.requirements = requirements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
