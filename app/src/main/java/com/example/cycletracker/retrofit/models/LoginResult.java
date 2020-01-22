package com.example.cycletracker.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("username")
    String username;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("email")
    String email;

    @SerializedName("branch")
    String branch;
}
