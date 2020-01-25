package com.example.cycletracker.retrofit.models;

import com.example.cycletracker.util.WApiConsts;
import com.google.gson.annotations.SerializedName;

public class GenericResponse {
    @SerializedName(WApiConsts.JSON_KEY_ERROR)
    Boolean error;

    @SerializedName(WApiConsts.JSON_KEY_MESSAGE)
    String message;

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
