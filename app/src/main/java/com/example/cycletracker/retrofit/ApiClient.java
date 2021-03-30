package com.example.cycletracker.retrofit;

import android.app.Application;

import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.util.WApiConsts;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private Retrofit retrofit;
    private static ApiClient client;
    private CycleIssuerClient cycleIssuerClient;
    private static String authToken;

    public ApiClient() {
        if(retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(WApiConsts.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static ApiClient getInstance() {
        if(client == null) {
            client = new ApiClient();
        }
        return client;
    }

    public static void setAuthToken(String authToken) {
        ApiClient.authToken = authToken;
        client = null;
    }

    public CycleIssuerClient getCycleIssuerClient() {
        if(cycleIssuerClient==null) {
            cycleIssuerClient = retrofit.create(CycleIssuerClient.class);
        }
        return cycleIssuerClient;
    }
}
