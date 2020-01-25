package com.example.cycletracker.retrofit.models;

import android.net.wifi.WpsInfo;

import com.example.cycletracker.util.WApiConsts;
import com.google.gson.annotations.SerializedName;

public class BookedCycleResp extends GenericResponse {

    @SerializedName(WApiConsts.JSON_KEY_CYCLE_ID)
    String cycleId;

    public String getCycleId() {
        return cycleId;
    }
}
