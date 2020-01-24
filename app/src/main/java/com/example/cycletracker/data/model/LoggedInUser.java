package com.example.cycletracker.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data class that captures user information for logged in users retrieved from DataRepository
 */
@Entity
public class LoggedInUser {

    @PrimaryKey
    @NonNull
    private String username;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "auth_token")
    private String authToken;

    public LoggedInUser(String username, String displayName, String authToken) {
        this.username = username;
        this.displayName = displayName;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAuthToken() {
        return authToken;
    }
}
