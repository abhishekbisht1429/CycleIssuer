package com.example.cycletracker.util;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cycletracker.home.viewmodel.HomeViewModel;
import com.example.cycletracker.lock.viewmodel.LockViewModel;
import com.example.cycletracker.login.viewmodel.LoginViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private static ViewModelFactory viewModelFactory;

    public static ViewModelFactory getInstance(Application application) {
        if(viewModelFactory==null) {
            viewModelFactory = new ViewModelFactory(application);
        }

        return viewModelFactory;
    }

    private ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(application);
        } else if(modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(application);
        }
        else if(modelClass.isAssignableFrom(LockViewModel.class)) {
            return (T) new LockViewModel(application);
        }
        return null;
    }
}
