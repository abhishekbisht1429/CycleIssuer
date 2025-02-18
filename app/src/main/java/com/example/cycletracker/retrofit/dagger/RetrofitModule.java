package com.example.cycletracker.retrofit.dagger;

import com.example.cycletracker.dagger.application.ApplicationScope;
import com.example.cycletracker.retrofit.ApiClient;

import dagger.Module;
import dagger.Provides;

@Module
public class RetrofitModule {

    @Provides
    @ApplicationScope
    ApiClient provideApiClient() {
        return new ApiClient();
    }
}
