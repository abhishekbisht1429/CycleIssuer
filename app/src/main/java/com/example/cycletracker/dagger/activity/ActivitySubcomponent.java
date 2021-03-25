package com.example.cycletracker.dagger.activity;

import com.example.cycletracker.activity.MainActivity;
import com.example.cycletracker.home.dagger.HomeSubcomponent;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent (modules = ActivityModule.class)
public interface ActivitySubcomponent {

    @Subcomponent.Builder
    interface Builder {
        ActivitySubcomponent build();

        Builder activityModule(ActivityModule activityModule);
    }

    void inject(MainActivity mainActivity);

    HomeSubcomponent.Builder homeSubComponentBuilder();

}
