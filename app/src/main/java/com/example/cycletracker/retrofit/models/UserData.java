package com.example.cycletracker.retrofit.models;

import com.example.cycletracker.util.WApiConsts;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName(WApiConsts.JSON_KEY_USERNAME)
    String username;

    @SerializedName(WApiConsts.JSON_KEY_FIRST_NAME)
    String firstName;

    @SerializedName(WApiConsts.JSON_KEY_LAST_NAME)
    String lastName;

    @SerializedName(WApiConsts.JSON_KEY_EMAIL)
    String email;

    @SerializedName(WApiConsts.JSON_KEY_BRANCH)
    String branch;

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }
}
