package com.example.cycletracker.data.localds.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cycletracker.model.LoggedInUser;

/**
 * Data class that captures user information for logged in users retrieved from DataRepository
 */
@Entity
public class LoggedInUserEntity {

    @PrimaryKey
    @NonNull
    private String username;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "auth_token")
    private String authToken;

    public LoggedInUserEntity(String username, String displayName, String authToken) {
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
