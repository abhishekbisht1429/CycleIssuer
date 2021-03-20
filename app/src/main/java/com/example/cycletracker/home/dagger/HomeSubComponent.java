package com.example.cycletracker.home.dagger;

import com.example.cycletracker.home.fragment.HistoryFragment;
import com.example.cycletracker.home.fragment.HomeFragment;
import com.example.cycletracker.home.fragment.MainFragment;
import com.example.cycletracker.home.fragment.ProfileFragment;
import com.example.cycletracker.home.fragment.SplashFragment;

import dagger.Subcomponent;

@Subcomponent
public interface HomeSubComponent {

    @Subcomponent.Builder
    public interface Builder {
        HomeSubComponent build();
    }

    void inject(MainFragment mainFragment);
    void inject(ProfileFragment profileFragment);
    void inject(SplashFragment splashFragment);
    void inject(HomeFragment homeFragment);
    void inject(HistoryFragment historyFragment);

}
