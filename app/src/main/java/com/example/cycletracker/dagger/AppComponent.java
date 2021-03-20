package com.example.cycletracker.dagger;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.data.dagger.DataModule;
import com.example.cycletracker.home.dagger.HomeSubComponent;
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

    HomeSubComponent.Builder homeSubComponentBuilder();
    LoginSubcomponent.Builder loginSubcomponentBuilder();
}
