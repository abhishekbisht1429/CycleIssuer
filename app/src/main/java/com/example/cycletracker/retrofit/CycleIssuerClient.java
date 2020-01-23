package com.example.cycletracker.retrofit;

import com.example.cycletracker.retrofit.models.LoginResp;
import com.example.cycletracker.retrofit.models.UserData;
import com.example.cycletracker.util.WApiConsts;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CycleIssuerClient {
    @FormUrlEncoded
    @POST("auth/users/me")
    Call<UserData> fetchUserDetails(@Field(WApiConsts.FORM_FIELD_USERNAME) String username,
                                    @Field(WApiConsts.FORM_FIELD_PASSWORD) String password);

    @POST("auth/token/login")
    Call<LoginResp> login(@Field(WApiConsts.FORM_FIELD_USERNAME) String username,
                          @Field(WApiConsts.FORM_FIELD_PASSWORD) String password);

}
