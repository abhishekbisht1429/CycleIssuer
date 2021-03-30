package com.example.cycletracker.retrofit;

import com.example.cycletracker.retrofit.model.BookedCycleResp;
import com.example.cycletracker.retrofit.model.GenericResponse;
import com.example.cycletracker.retrofit.model.LoginRespData;
import com.example.cycletracker.retrofit.model.UserData;
import com.example.cycletracker.util.WApiConsts;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CycleIssuerClient {

    @GET("auth/users/me/")
    Call<UserData> fetchUserDetails(@Header("Authorization") String authToken);

    @FormUrlEncoded
    @POST("auth/token/login")
    Call<LoginRespData> login(@Field(WApiConsts.FORM_FIELD_USERNAME) String username,
                              @Field(WApiConsts.FORM_FIELD_PASSWORD) String password);

    @POST("auth/token/logout")
    Call<String> logout(@Header("Authorization") String authToken);

    @FormUrlEncoded
    @POST("cycle/book")
    Call<BookedCycleResp> book(@Field(WApiConsts.FORM_FIELD_QRCODE) String qrcode,
                               @Header("Authorization") String authToken);

    @GET("cycle/booked")
    Call<BookedCycleResp> getBookedCycleId();

    @FormUrlEncoded
    @POST("cycle/lock")
    Call<GenericResponse> lock(@Field(WApiConsts.JSON_KEY_CYCLE_ID) int cycleId,
                               @Field(WApiConsts.JSON_KEY_LOCK_VALUE) boolean locked,
                               @Header("Authorization") String authKey);

    @FormUrlEncoded
    @POST("cycle/return")
    Call<GenericResponse> returnCycle(@Field(WApiConsts.JSON_KEY_CYCLE_ID) int cycleId,
                                      @Header("Authorization") String authToken);
}
