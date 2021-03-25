package com.example.cycletracker;

import android.app.Application;

import com.example.cycletracker.dagger.application.AppComponent;
import com.example.cycletracker.dagger.application.AppModule;
import com.example.cycletracker.dagger.application.DaggerAppComponent;

public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);
        super.onCreate();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
