package com.example.golift.model;

public class User {

    public static final String N_KEY = "UNAMEV";
    public static final String PASS_KEY = "PASSV";

    String username;
    String password;

    // Default constructor required for Firebase
    public User() {
    }

    // Constructor with parameters (optional but useful)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}