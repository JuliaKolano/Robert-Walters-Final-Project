package com.finalproject.code.classes;

// Implemented the Singleton pattern, since there can only be one user logged in
public class User {
    // single user instance for the whole application
    private static User userInstance;

    // user object variables
    private String username;
    private String profilePicturePath;

    // Constructor
    public User(String username) {
        this.username = username;
        this.profilePicturePath = "user.png";
    }

    // Provide access to the user instance to the rest of application with the username
    public static User getInstance(String username) {
        // allow for creation and retrieval
        if (userInstance == null) {
            userInstance = new User(username);
        }
        return userInstance;
    }

    // Provide access to the user instance to the rest of application without the username
    public static User getInstance() {
        // Only allow for retrieval
        if (userInstance != null) {
            return userInstance;
        } else {
            return null;
        }
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
