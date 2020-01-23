package com.example.cycletracker.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String username;
    private String displayName;

    public LoggedInUser(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }
}
