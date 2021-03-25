package com.example.cycletracker.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cycletracker.dagger.application.ApplicationScope;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.home.dagger.HomeScope;

import java.util.concurrent.ExecutorService;

@ApplicationScope
public class ViewModelFactory implements ViewModelProvider.Factory {

    private DataRepository dataRepository;
    private ExecutorService executorService;

    public ViewModelFactory(DataRepository dataRepository, ExecutorService executorService) {
        this.dataRepository = dataRepository;
        this.executorService = executorService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(BicycleViewModel.class)) {
            return (T) new BicycleViewModel(dataRepository, executorService);
        } else if (modelClass.isAssignableFrom(LoggedInUserViewModel.class)) {
            return (T) new BicycleViewModel(dataRepository, executorService);
        }
        return null;
    }
}
