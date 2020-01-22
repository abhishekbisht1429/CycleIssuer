package com.example.cycletracker.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "";
    private Retrofit retrofit;
    private static ApiClient client;
    private CycleIssuerClient cycleIssuerClient;
    private ApiClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
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

    public CycleIssuerClient getCycleIssuerClient() {
        if(cycleIssuerClient==null) {
            cycleIssuerClient = retrofit.create(CycleIssuerClient.class);
        }
        return cycleIssuerClient;
    }
}
