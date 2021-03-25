package com.example.cycletracker.home.dagger;

import com.example.cycletracker.home.dagger.pager.PagerSubComponent;
import com.example.cycletracker.home.fragment.HomeFragment;
import com.example.cycletracker.login.fragment.SplashFragment;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeSubcomponent {

    @Subcomponent.Builder
    public interface Builder {
        HomeSubcomponent build();

        Builder homeModule(HomeModule homeModule);
    }

    void inject(HomeFragment homeFragment);

    PagerSubComponent.Builder pagerSubComponentBuilder();
}
