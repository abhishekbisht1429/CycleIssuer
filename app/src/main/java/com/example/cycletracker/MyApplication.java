package com.example.cycletracker;

import android.app.Application;

import com.example.cycletracker.dagger.AppComponent;
import com.example.cycletracker.dagger.AppModule;
import com.example.cycletracker.dagger.DaggerAppComponent;

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
