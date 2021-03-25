package com.example.cycletracker.dagger.application;

import android.app.Application;

import com.example.cycletracker.dagger.activity.ActivityScope;
import com.example.cycletracker.dagger.activity.ActivitySubcomponent;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.login.dagger.LoginSubcomponent;
import com.example.cycletracker.viewmodel.ViewModelFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents =
        {
                ActivitySubcomponent.class,
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

    @Provides
    @ApplicationScope
    ViewModelFactory viewModelFactory(DataRepository dataRepository, ExecutorService executorService) {
        return new ViewModelFactory(dataRepository, executorService);
    }

}
