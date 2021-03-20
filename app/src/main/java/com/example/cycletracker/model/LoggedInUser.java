package com.example.cycletracker.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class LoggedInUser {


    private String username;

    private String displayName;

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

//    private static class Builder {
//        private String username;
//        private String displayName;
//        private String authToken;
//
//        public LoggedInUser build(){
//            LoggedInUser loggedInUser = new LoggedInUser();
//
//            loggedInUser.username = username;
//            loggedInUser.displayName = displayName;
//            loggedInUser.authToken = authToken;
//
//            return loggedInUser;
//        }
//
//        public Builder username(String username) {
//            this.username = username;
//            return this;
//        }
//
//        public Builder displayName(String displayName) {
//            this.displayName = displayName;
//
//            return this;
//        }
//
//        public Builder authToken()
//    }
}
