package com.example.cycletracker.login.dagger;

import com.example.cycletracker.login.fragment.LoginFragment;
import com.example.cycletracker.login.fragment.SplashFragment;

import dagger.Subcomponent;

@Subcomponent
public interface LoginSubcomponent {

    @Subcomponent.Builder
    interface Builder {
        LoginSubcomponent build();
    }

    void inject(LoginFragment loginFragment);
    void inject(SplashFragment splashFragment);
}
