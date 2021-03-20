package com.example.cycletracker.dagger;

import android.app.Application;
import android.content.Context;

import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.home.dagger.HomeSubComponent;
import com.example.cycletracker.login.dagger.LoginSubcomponent;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents =
        {
                HomeSubComponent.class,
                LoginSubcomponent.class
        })
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

}
