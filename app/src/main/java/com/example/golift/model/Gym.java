package com.example.golift.model;

public class Gym {
    private long id;
    private String name;
    private int distance;
    private String description;

    public Gym() {
    }

    public Gym(long id, String name, int distance, String description) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}