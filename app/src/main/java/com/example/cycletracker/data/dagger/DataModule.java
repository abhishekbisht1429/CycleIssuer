package com.example.cycletracker.data.dagger;

import android.app.Application;

import androidx.room.Room;

import com.example.cycletracker.dagger.application.ApplicationScope;
import com.example.cycletracker.data.localds.LocalDataSource;
import com.example.cycletracker.data.remoteds.RemoteDataSource;
import com.example.cycletracker.retrofit.ApiClient;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @ApplicationScope
    LocalDataSource provideLocalDatabase(Application application) {
        return Room.databaseBuilder(application, LocalDataSource.class, "Local Database").build();
    }

    @Provides
    @ApplicationScope
    RemoteDataSource provideRemoteDataSource(ApiClient apiClient) {
        return new RemoteDataSource(apiClient);
    }
}
