package com.example.cycletracker.retrofit.model;

import com.example.cycletracker.util.WApiConsts;
import com.google.gson.annotations.SerializedName;

public class BookedCycleResp extends GenericResponse {

    @SerializedName(WApiConsts.JSON_KEY_CYCLE_ID)
    Integer cycleId;

    public Integer getCycleId() {
        return cycleId;
    }
}
