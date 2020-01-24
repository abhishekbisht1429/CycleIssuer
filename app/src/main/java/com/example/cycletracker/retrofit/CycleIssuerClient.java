package com.example.cycletracker.retrofit;

import com.example.cycletracker.retrofit.models.LoginRespData;
import com.example.cycletracker.retrofit.models.UserData;
import com.example.cycletracker.util.WApiConsts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CycleIssuerClient {

    @GET("auth/users/me/")
    Call<UserData> fetchUserDetails();

    @FormUrlEncoded
    @POST("auth/token/login")
    Call<LoginRespData> login(@Field(WApiConsts.FORM_FIELD_USERNAME) String username,
                              @Field(WApiConsts.FORM_FIELD_PASSWORD) String password);

    @POST("auth/token/logout")
    Call<String> logout();

}
