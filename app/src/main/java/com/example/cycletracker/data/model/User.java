package com.example.cycletracker.data.model;

public class User {
    private String sapId;
    private String password;

    public User(String sapId, String password) {
        this.sapId = sapId;
        this.password = password;
    }

    public String getSapId() {
        return sapId;
    }

    public String getPassword() {
        return password;
    }
}
