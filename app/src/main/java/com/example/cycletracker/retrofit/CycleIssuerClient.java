package com.example.cycletracker.retrofit;

import com.example.cycletracker.retrofit.models.LoginResult;
import com.example.cycletracker.util.WApiConsts;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CycleIssuerClient {
    @POST
    @FormUrlEncoded
    Response<LoginResult> login(@Field(WApiConsts.FORM_FIELD_USERNAME)String username,
                                @Field(WApiConsts.FORM_FIELD_PASSWORD)String password);

}
