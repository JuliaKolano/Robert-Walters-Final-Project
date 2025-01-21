package com.finalproject.code.classes;

public class User {
    private String username;
    private String profilePicturePath;

    // Constructors
    public User(String username, String profilePicturePath) {
        this.username = username;
        this.profilePicturePath = profilePicturePath;
    }

    public User(String username) {
        this.username = username;
        this.profilePicturePath = "user.png";
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
