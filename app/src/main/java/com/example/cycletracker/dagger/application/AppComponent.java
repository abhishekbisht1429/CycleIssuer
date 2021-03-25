package com.example.cycletracker.dagger.application;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.data.dagger.DataModule;
import com.example.cycletracker.dagger.activity.ActivitySubcomponent;
import com.example.cycletracker.login.dagger.LoginSubcomponent;
import com.example.cycletracker.retrofit.dagger.RetrofitModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@Component (modules = {
                       AndroidInjectionModule.class,
                       DataModule.class,
                       AppModule.class,
                       RetrofitModule.class})
public interface AppComponent {
    void inject(MyApplication application);

    ActivitySubcomponent.Builder activitySubComponentBuilder();
    LoginSubcomponent.Builder loginSubcomponentBuilder();
}
