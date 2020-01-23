package com.example.cycletracker.retrofit.models;

import com.example.cycletracker.util.WApiConsts;
import com.google.gson.annotations.SerializedName;

public class LoginResp {
    @SerializedName(WApiConsts.JSON_KEY_AUTH_TOKEN)
    String authToken;
}
